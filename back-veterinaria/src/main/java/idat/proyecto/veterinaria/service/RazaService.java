package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

import idat.proyecto.veterinaria.entity.Raza;

public interface RazaService {

	public abstract ResponseEntity<?> insert(Raza raza);
	public abstract ResponseEntity<?> update(Integer id, Raza raza);
	public abstract ResponseEntity<?> delete(Integer id);
	public abstract ResponseEntity<?> findById(Integer id);
	public abstract ResponseEntity<?> findAll();
	public abstract ResponseEntity<?> findAllCustom();
	public abstract ResponseEntity<?> findAllMapper();
}
