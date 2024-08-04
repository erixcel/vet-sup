package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

import idat.proyecto.veterinaria.entity.Categoria;

public interface CategoriaService {

	public abstract ResponseEntity<?> insert(Categoria categoria);
	public abstract ResponseEntity<?> update(Integer id, Categoria categoria);
	public abstract ResponseEntity<?> delete(Integer id);
	public abstract ResponseEntity<?> findById(Integer id);
	public abstract ResponseEntity<?> findAll();
	public abstract ResponseEntity<?> findAllCustom();
	public abstract ResponseEntity<?> findAllMapper();
}
