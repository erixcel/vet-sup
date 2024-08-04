package idat.proyecto.veterinaria.service;

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

import idat.proyecto.veterinaria.compound.BoletaProductoId;
import idat.proyecto.veterinaria.custom.BoletaCustom;
import idat.proyecto.veterinaria.entity.Boleta;
import idat.proyecto.veterinaria.entity.BoletaProducto;
import idat.proyecto.veterinaria.entity.Producto;
import idat.proyecto.veterinaria.entity.Usuario;
import idat.proyecto.veterinaria.enumerated.BoletaEstado;
import idat.proyecto.veterinaria.generator.GenerarBoletaPDF;
import idat.proyecto.veterinaria.mapper.BoletaMapper;
import idat.proyecto.veterinaria.repository.BoletaRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class BoletaServiceImpl implements BoletaService{

	@Autowired
	private BoletaRepository boletaRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	@Transactional
	public ResponseEntity<?> insert(Boleta boleta, String authorizationHeader) {
		
		TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		ResponseEntity<?> statusCliente = clienteService.findById(boleta.getCliente().getId());
		if (statusCliente.getStatusCode() != HttpStatus.OK) return statusCliente;
		
		ResponseEntity<?> statusUsuario = authService.getUsuario(authorizationHeader);
		if (statusUsuario.getStatusCode() != HttpStatus.OK) return statusUsuario;
		Usuario usuarioFound = (Usuario) statusUsuario.getBody();
		
		Set<BoletaProducto> detalleProducto = new HashSet<>();
		
		for(BoletaProducto boletaProducto : boleta.getDetalle_producto()) {
			
			ResponseEntity<?> statusProducto = productoService.findById(boletaProducto.getProducto().getId());
			if (statusProducto.getStatusCode() != HttpStatus.OK) {
				transactionStatus.setRollbackOnly();
				return statusProducto;
			} 
			Producto producto = (Producto) statusProducto.getBody();
			
			Integer cantidad = boletaProducto.getCantidad();
			Integer stock = producto.getStock();
			if (stock < cantidad) {
				transactionStatus.setRollbackOnly();
				return new ResponseEntity<>(Response.createMap("Stock insufficient!", producto.getId()), HttpStatus.BAD_REQUEST);
			}
			producto.setStock(stock - cantidad);
			
			boletaProducto.setBoleta(boleta);
			boletaProducto.setProducto(producto);
			boletaProducto.calcularTotal();
			detalleProducto.add(boletaProducto);
		}
		
		boleta.setUsuario(usuarioFound);
		boleta.setDetalle_producto(detalleProducto);
		boleta.calcularPrecioFinal();
		
		boletaRepository.save(boleta);
		return new ResponseEntity<>(Response.createMap("Boleta create!", boleta.getId()), HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Boleta boleta, String authorizationHeader) {
		
		TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		ResponseEntity<?> statusBoleta = findById(id);
		if (statusBoleta.getStatusCode() != HttpStatus.OK) return statusBoleta;
		Boleta boletaFound = (Boleta) statusBoleta.getBody();
		
		ResponseEntity<?> statusCliente = clienteService.findById(boleta.getCliente().getId());
		if (statusCliente.getStatusCode() != HttpStatus.OK) return statusCliente;
		
		ResponseEntity<?> statusUsuario = authService.getUsuario(authorizationHeader);
		if (statusUsuario.getStatusCode() != HttpStatus.OK) return statusUsuario;
		Usuario usuarioFound = (Usuario) statusUsuario.getBody();
		
		boleta.setId(id);
		boleta.setFecha_creacion(boletaFound.getFecha_creacion());
		boleta.setEstado(BoletaEstado.emitida);
		boleta.setMensaje(null);
		if(!boleta.getTipo_pago().equals("efectivo")) boleta.setVuelto(0.0);
		
		Set<BoletaProducto> detalleProducto = new HashSet<>();
		Set<BoletaProducto> detalleProductoOld = new HashSet<>(boletaFound.getDetalle_producto());
		
		for(BoletaProducto boletaProducto : boleta.getDetalle_producto()) {
			
			ResponseEntity<?> statusProducto = productoService.findById(boletaProducto.getProducto().getId());
			if (statusProducto.getStatusCode() != HttpStatus.OK) {
				transactionStatus.setRollbackOnly();
				return statusProducto;
			}
			Producto producto = (Producto) statusProducto.getBody();

			
			Integer cantidad = boletaFound.getDetalle_producto().stream()
				    .filter(bp -> bp.getProducto().getId().equals(producto.getId()))
				    .map(bp -> boletaProducto.getCantidad() - bp.getCantidad())
				    .findFirst()
				    .orElse(boletaProducto.getCantidad());
			
			Integer stock = producto.getStock();
		
			detalleProductoOld.removeIf(dt -> dt.getProducto().getId().equals(producto.getId()));
			
			if (stock < cantidad) {
				transactionStatus.setRollbackOnly();
				return new ResponseEntity<>(Response.createMap("Stock insufficient!", producto.getId()), HttpStatus.BAD_REQUEST);
			}
			
			producto.setStock(stock - cantidad);
			
	        BoletaProductoId idDetalle = new BoletaProductoId();
	        idDetalle.setBoleta_id(boleta.getId());
	        idDetalle.setProducto_id(producto.getId());
	        boletaProducto.setId(idDetalle);
	        boletaProducto.setBoleta(boleta);
	        boletaProducto.setProducto(producto);
	        boletaProducto.calcularTotal();
	        detalleProducto.add(boletaProducto);
		}
		
		for(BoletaProducto boletaProducto : detalleProductoOld) {
			Producto producto = boletaProducto.getProducto();
			producto.setStock(producto.getStock() + boletaProducto.getCantidad());
		}
		
		boleta.setUsuario(usuarioFound);
		boleta.setDetalle_producto(detalleProducto);
		boleta.calcularPrecioFinal();
		
		boletaRepository.save(boleta);
		return new ResponseEntity<>(Response.createMap("Boleta update!", boleta.getId()), HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusBoleta = findById(id);
		if (statusBoleta.getStatusCode() != HttpStatus.OK) return statusBoleta;
		Boleta boletaFound = (Boleta) statusBoleta.getBody();
		Set<BoletaProducto> boletaProducto = new HashSet<>();
		boletaFound.setDetalle_producto(boletaProducto);
		boletaRepository.delete(boletaFound);
		return new ResponseEntity<>(Response.createMap("Boleta delete!", id), HttpStatus.OK);
		
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> anular(Integer id, String mensaje) {
		
		ResponseEntity<?> statusBoleta = findById(id);
		if (statusBoleta.getStatusCode() != HttpStatus.OK) return statusBoleta;
		Boleta boletaFound = (Boleta) statusBoleta.getBody();
		boletaFound.setMensaje(mensaje);
		boletaFound.setEstado(BoletaEstado.anulada);
		
		return new ResponseEntity<>(Response.createMap("Boleta anulada!", id), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Boleta boleta = boletaRepository.findById(id).orElse(null);
		if(boleta == null) {
			return new ResponseEntity<>(Response.createMap("Boleta not found!", id), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(boleta, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findNewId() {
		Integer newId = boletaRepository.findNewId();
		return new ResponseEntity<>(newId, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Boleta> coleccion = boletaRepository.findAll().stream().collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<BoletaCustom> coleccion = boletaRepository.findAllCustom();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<BoletaMapper> coleccion = boletaRepository.findAllMapper();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> descargar(Integer id) {
		ResponseEntity<?> statusBoleta = findById(id);
		if (statusBoleta.getStatusCode() != HttpStatus.OK) return statusBoleta;
		Boleta boletaFound = (Boleta) statusBoleta.getBody();
		
		GenerarBoletaPDF generator = new GenerarBoletaPDF(boletaFound);
	    
		try {
			Resource file = generator.generatePdfResource();
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "Boleta N°" + id + ".pdf" + "\"").body(file);
		} catch (Exception e) {
			return new ResponseEntity<>(Response.createMap("Error al descargar Boleta " + id + "!", id), HttpStatus.BAD_REQUEST);
		}
	    
	}
	
	@Override
	public ResponseEntity<?> previsualizar(Integer id) {
	    ResponseEntity<?> statusBoleta = findById(id);
	    if (statusBoleta.getStatusCode() != HttpStatus.OK) return statusBoleta;
	    Boleta boletaFound = (Boleta) statusBoleta.getBody();
	    
	    GenerarBoletaPDF generator = new GenerarBoletaPDF(boletaFound);
	    
	    try {
	        Resource file = generator.generatePdfResource();
	        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + "Boleta N°" + id + ".pdf" + "\"").body(file);
	    } catch (Exception e) {
	        return new ResponseEntity<>(Response.createMap("Error al descargar Boleta " + id + "!", id), HttpStatus.BAD_REQUEST);
	    }
	    
	}

}