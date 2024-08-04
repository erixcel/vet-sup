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
import idat.proyecto.veterinaria.custom.ProveedorCustom;
import idat.proyecto.veterinaria.entity.Producto;
import idat.proyecto.veterinaria.entity.Proveedor;
import idat.proyecto.veterinaria.mapper.ProveedorMapper;
import idat.proyecto.veterinaria.repository.ProveedorRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class ProveedorServiceImpl implements ProveedorService{

	@Autowired
	private ProveedorRepository proveedorRepository;
	
	private GoogleStorage storage = new GoogleStorage("proveedores","vet-proveedor-");

	@Override
	@Transactional
	public ResponseEntity<?> insert(Proveedor proveedor) {
		proveedorRepository.save(proveedor);
		return new ResponseEntity<>(Response.createMap("Proveedor create!", proveedor.getId()), HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Proveedor proveedor) {
		
		ResponseEntity<?> statusProveedor = findById(id);
		if (statusProveedor.getStatusCode() != HttpStatus.OK) return statusProveedor;
		Proveedor proveedorFound = (Proveedor) statusProveedor.getBody();
		
		proveedor.setId(id);
		proveedor.setFoto(proveedorFound.getFoto());
		proveedor.setEliminado(false);
		proveedorRepository.save(proveedor);
		return new ResponseEntity<>(Response.createMap("Proveedor update!", proveedor.getId()), HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusProveedor = findById(id);
		if (statusProveedor.getStatusCode() != HttpStatus.OK) return statusProveedor;
		Proveedor proveedorFound = (Proveedor) statusProveedor.getBody();
		proveedorFound.setEliminado(true);
		
		return new ResponseEntity<>(Response.createMap("Proveedor delete!", id), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Proveedor proveedor = proveedorRepository.findById(id).orElse(null);
		if(proveedor == null || proveedor.getEliminado()) {
			return new ResponseEntity<>(Response.createMap("Proveedor not found!", id), HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(proveedor, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findProductosById(Integer id) {
		
		ResponseEntity<?> statusProveedor = findById(id);
		if (statusProveedor.getStatusCode() != HttpStatus.OK) return statusProveedor;
		Proveedor proveedorFound = (Proveedor) statusProveedor.getBody();
		
		Collection<Producto> productos = proveedorFound.getProductos();
		return new ResponseEntity<>(productos, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Proveedor> coleccion = proveedorRepository.findAll().stream().filter(proveedor -> !proveedor.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> setFoto(Integer id, MultipartFile file) {
		
		ResponseEntity<?> statusProveedor = findById(id);
		if (statusProveedor.getStatusCode() != HttpStatus.OK) return statusProveedor;
			
		Proveedor proveedorFound = (Proveedor) statusProveedor.getBody();

		try {
			proveedorFound.setFoto(storage.uploadImage(id.toString(), file));
			return new ResponseEntity<>("Foto Proveedor saved!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Ocurrio un error, intente nuevamente...", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<ProveedorCustom> coleccion = proveedorRepository.findAllCustom();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<ProveedorMapper> coleccion = proveedorRepository.findAllMapper();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
}
