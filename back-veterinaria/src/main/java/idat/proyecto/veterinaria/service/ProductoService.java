package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import idat.proyecto.veterinaria.entity.Producto;

public interface ProductoService {
	public abstract ResponseEntity<?> insert(Producto producto);
	public abstract ResponseEntity<?> update(Integer id, Producto producto);
	public abstract ResponseEntity<?> delete(Integer id);
	public abstract ResponseEntity<?> findById(Integer id);
	public abstract ResponseEntity<?> findAll();
	public abstract ResponseEntity<?> findAllCustom();
	public abstract ResponseEntity<?> findAllMapper();
	public abstract ResponseEntity<?> setFoto(Integer id, MultipartFile file);
}
