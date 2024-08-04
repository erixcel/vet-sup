package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import idat.proyecto.veterinaria.cloud.GoogleStorage;
import idat.proyecto.veterinaria.custom.ProductoCustom;
import idat.proyecto.veterinaria.entity.Producto;
import idat.proyecto.veterinaria.entity.Proveedor;
import idat.proyecto.veterinaria.mapper.ProductoMapper;
import idat.proyecto.veterinaria.repository.ProductoRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ProveedorService proveedorService;
	
	private GoogleStorage storage = new GoogleStorage("productos","vet-producto-");

	@Override
	@Transactional
	public ResponseEntity<?> insert(Producto producto) {
		
		ResponseEntity<?> statusCategoria = categoriaService.findById(producto.getCategoria().getId());
		if (statusCategoria.getStatusCode() != HttpStatus.OK) return statusCategoria;
		
		for(Proveedor proveedor : producto.getProveedores()){
			ResponseEntity<?> statusProveedor = proveedorService.findById(proveedor.getId());
			if (statusProveedor.getStatusCode() != HttpStatus.OK) return statusProveedor;
		}
		
		productoRepository.save(producto);
		return new ResponseEntity<>(Response.createMap("Producto create!", producto.getId()), HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Producto producto) {
		
		ResponseEntity<?> statusProducto = findById(id);
		if (statusProducto.getStatusCode() != HttpStatus.OK) return statusProducto;
		Producto productoFound = (Producto) statusProducto.getBody();
		
		ResponseEntity<?> statusCategoria = categoriaService.findById(producto.getCategoria().getId());
		if (statusCategoria.getStatusCode() != HttpStatus.OK) return statusCategoria;
		
		for(Proveedor proveedor : producto.getProveedores()){
			ResponseEntity<?> statusProveedor = proveedorService.findById(proveedor.getId());
			if (statusProveedor.getStatusCode() != HttpStatus.OK) return statusProveedor;
		}
		
		producto.setId(id);
		producto.setFecha_creacion(productoFound.getFecha_creacion());
		producto.setFoto(productoFound.getFoto());
		producto.setEliminado(false);
		productoRepository.save(producto);
		return new ResponseEntity<>(Response.createMap("Producto update!", producto.getId()), HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusProducto = findById(id);
		if (statusProducto.getStatusCode() != HttpStatus.OK) return statusProducto;
		Producto productoFound = (Producto) statusProducto.getBody();
		productoFound.setEliminado(true);
		
		return new ResponseEntity<>(Response.createMap("Producto delete!", id), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Producto producto = productoRepository.findById(id).orElse(null);
		if(producto == null || producto.getEliminado()) {
			return new ResponseEntity<>(Response.createMap("Producto not found!", id), HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(producto, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Producto> coleccion = productoRepository.findAll().stream().filter(producto -> !producto.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<ProductoCustom> coleccion = productoRepository.findAllCustom();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<ProductoMapper> coleccion = productoRepository.findAllMapper();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> setFoto(Integer id, MultipartFile file) {
		
		ResponseEntity<?> statusProducto = findById(id);
		if (statusProducto.getStatusCode() != HttpStatus.OK) return statusProducto;
		Producto productoFound = (Producto) statusProducto.getBody();
		
		try {
			productoFound.setFoto(storage.uploadImage(id.toString(), file));
			return new ResponseEntity<>("Foto Producto saved!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Ocurrio un error, intente nuevamente...", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
