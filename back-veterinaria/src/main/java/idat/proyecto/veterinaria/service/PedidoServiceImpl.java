package idat.proyecto.veterinaria.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.google.common.net.HttpHeaders;

import idat.proyecto.veterinaria.compound.PedidoProductoId;
import idat.proyecto.veterinaria.custom.PedidoCustom;
import idat.proyecto.veterinaria.entity.Pedido;
import idat.proyecto.veterinaria.entity.PedidoProducto;
import idat.proyecto.veterinaria.entity.Producto;
import idat.proyecto.veterinaria.entity.Proveedor;
import idat.proyecto.veterinaria.entity.Usuario;
import idat.proyecto.veterinaria.generator.GenerarPedidoPDF;
import idat.proyecto.veterinaria.mapper.PedidoMapper;
import idat.proyecto.veterinaria.repository.PedidoRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class PedidoServiceImpl implements PedidoService{

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProveedorService proveedorService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	@Transactional
	public ResponseEntity<?> insert(Pedido pedido, String authorizationHeader) {
		
		ResponseEntity<?> statusProveedor = proveedorService.findById(pedido.getProveedor().getId());
		if (statusProveedor.getStatusCode() != HttpStatus.OK) return statusProveedor;
		Proveedor proveedorFound = (Proveedor) statusProveedor.getBody();
		
		ResponseEntity<?> statusUsuario = authService.getUsuario(authorizationHeader);
		if (statusUsuario.getStatusCode() != HttpStatus.OK) return statusUsuario;
		Usuario usuarioFound = (Usuario) statusUsuario.getBody();
		
		Set<Producto> listaProductos = proveedorFound.getProductos();
		Set<PedidoProducto> detalleProducto = new HashSet<>();
		
		for(PedidoProducto pedidoProducto : pedido.getDetalle_producto()) {
			
			ResponseEntity<?> statusProducto = productoService.findById(pedidoProducto.getProducto().getId());
			if (statusProducto.getStatusCode() != HttpStatus.OK) return statusProducto;
			Producto producto = (Producto) statusProducto.getBody();
			
			if (!listaProductos.contains(producto)) {
				return new ResponseEntity<>(Response.createMap("Proveedor no vende este producto!", producto.getId()), HttpStatus.BAD_REQUEST);
		    } 
			
			pedidoProducto.setPedido(pedido);
			pedidoProducto.setProducto(producto);
			pedidoProducto.calcularImporte();
			detalleProducto.add(pedidoProducto);
		}
		
		pedido.setUsuario(usuarioFound);
		pedido.setDetalle_producto(detalleProducto);
		pedido.calcularTotal();
		
		pedidoRepository.save(pedido);
		return new ResponseEntity<>(Response.createMap("Pedido create!", pedido.getId()), HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Pedido pedido, String authorizationHeader) {
		
		TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		ResponseEntity<?> statusPedido = findById(id);
		if (statusPedido.getStatusCode() != HttpStatus.OK) return statusPedido;
		Pedido pedidoFound = (Pedido) statusPedido.getBody();
		
		ResponseEntity<?> statusProveedor = proveedorService.findById(pedido.getProveedor().getId());
		if (statusProveedor.getStatusCode() != HttpStatus.OK) return statusProveedor;
		Proveedor proveedorFound = (Proveedor) statusProveedor.getBody();
		
		ResponseEntity<?> statusUsuario = authService.getUsuario(authorizationHeader);
		if (statusUsuario.getStatusCode() != HttpStatus.OK) return statusUsuario;
		Usuario usuarioFound = (Usuario) statusUsuario.getBody();
		
		pedido.setId(id);
		pedido.setFecha_emision(pedidoFound.getFecha_emision());
		pedido.setEstado("enviada");
		pedido.setMensaje(null);
		pedido.setFecha_entrega(null);
		
		Set<Producto> listaProductos = proveedorFound.getProductos();
		Set<PedidoProducto> detalleProducto = new HashSet<>();
		
		for(PedidoProducto pedidoProducto : pedido.getDetalle_producto()) {
			
			ResponseEntity<?> statusProducto = productoService.findById(pedidoProducto.getProducto().getId());
			if (statusProducto.getStatusCode() != HttpStatus.OK) return statusProducto;
			
			Producto producto = (Producto) statusProducto.getBody();
			
			if (!listaProductos.contains(producto)) {
				transactionStatus.setRollbackOnly();
				return new ResponseEntity<>(Response.createMap("Proveedor no vende este producto!", producto.getId()), HttpStatus.BAD_REQUEST);
		    } 
			
	        PedidoProductoId idDetalle = new PedidoProductoId();
	        idDetalle.setPedido_id(pedido.getId());
	        idDetalle.setProducto_id(producto.getId());
	        pedidoProducto.setId(idDetalle);
	        pedidoProducto.setPedido(pedido);
	        pedidoProducto.setProducto(producto);
	        pedidoProducto.calcularImporte();
	        detalleProducto.add(pedidoProducto);
		}
		
		
		pedido.setUsuario(usuarioFound);
		pedido.setDetalle_producto(detalleProducto);
		pedido.calcularTotal();
		
		pedidoRepository.save(pedido);
		return new ResponseEntity<>(Response.createMap("Pedido update!", pedido.getId()), HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusPedido = findById(id);
		if (statusPedido.getStatusCode() != HttpStatus.OK) return statusPedido;
		Pedido pedidoFound = (Pedido) statusPedido.getBody();
		Set<PedidoProducto> pedidoProducto = new HashSet<>();
		pedidoFound.setDetalle_producto(pedidoProducto);
		pedidoRepository.delete(pedidoFound);
		return new ResponseEntity<>(Response.createMap("Pedido delete!", id), HttpStatus.OK);
		
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> anular(Integer id, String mensaje) {
		
		ResponseEntity<?> statusPedido = findById(id);
		if (statusPedido.getStatusCode() != HttpStatus.OK) return statusPedido;
		Pedido pedidoFound = (Pedido) statusPedido.getBody();
		
		if(pedidoFound.getEstado() == "recibida") {
			return new ResponseEntity<>(Response.createMap("Pedido ya ha sido recibida!", id), HttpStatus.BAD_REQUEST);
		}
		
		pedidoFound.setMensaje(mensaje);
		pedidoFound.setEstado("anulada");
		
		return new ResponseEntity<>(Response.createMap("Pedido anulada!", id), HttpStatus.OK);
		
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> recibir(Integer id) {
		
		ResponseEntity<?> statusPedido = findById(id);
		if (statusPedido.getStatusCode() != HttpStatus.OK) return statusPedido;
		Pedido pedidoFound = (Pedido) statusPedido.getBody();
		
		if(pedidoFound.getEstado() == "recibida") {
			return new ResponseEntity<>(Response.createMap("Pedido ya ha sido recibida!", id), HttpStatus.BAD_REQUEST);
		}
		
		if(pedidoFound.getEstado() == "anulada") {
			return new ResponseEntity<>(Response.createMap("Pedido ya ha sido anulada!", id), HttpStatus.BAD_REQUEST);
		}
		
		pedidoFound.setEstado("recibida");
		pedidoFound.setFecha_entrega(LocalDateTime.now());
		
		for(PedidoProducto pedidoProducto : pedidoFound.getDetalle_producto()) {		
			Producto producto = pedidoProducto.getProducto();
			producto.setStock(producto.getStock() + pedidoProducto.getCantidad());
		}
		
		return new ResponseEntity<>(Response.createMap("Pedido recibida!", id), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Pedido pedido = pedidoRepository.findById(id).orElse(null);
		if(pedido == null) {
			return new ResponseEntity<>(Response.createMap("Pedido not found!", id), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(pedido, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findNewId() {
		Integer newId = pedidoRepository.findNewId();
		return new ResponseEntity<>(newId, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAll() {
		Collection<Pedido> coleccion = pedidoRepository.findAll().stream().collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<PedidoCustom> coleccion = pedidoRepository.findAllCustom();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<PedidoMapper> coleccion = pedidoRepository.findAllMapper();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> descargar(Integer id) {
		ResponseEntity<?> statusPedido = findById(id);
		if (statusPedido.getStatusCode() != HttpStatus.OK) return statusPedido;
		Pedido pedidoFound = (Pedido) statusPedido.getBody();
		
		GenerarPedidoPDF generator = new GenerarPedidoPDF(pedidoFound);
	    
		try {
			Resource file = generator.generatePdfResource();
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "Pedido N°" + id + ".pdf" + "\"").body(file);
		} catch (Exception e) {
			return new ResponseEntity<>(Response.createMap("Error al descargar Pedido " + id + "!", id), HttpStatus.BAD_REQUEST);
		}
	    
	}
	
	@Override
	public ResponseEntity<?> previsualizar(Integer id) {
		ResponseEntity<?> statusPedido = findById(id);
		if (statusPedido.getStatusCode() != HttpStatus.OK) return statusPedido;
		Pedido pedidoFound = (Pedido) statusPedido.getBody();
		
		GenerarPedidoPDF generator = new GenerarPedidoPDF(pedidoFound);
	    
		try {
			Resource file = generator.generatePdfResource();
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + "Pedido N°" + id + ".pdf" + "\"").body(file);
		} catch (Exception e) {
			return new ResponseEntity<>(Response.createMap("Error al descargar Pedido " + id + "!", id), HttpStatus.BAD_REQUEST);
		}
	    
	}

}
