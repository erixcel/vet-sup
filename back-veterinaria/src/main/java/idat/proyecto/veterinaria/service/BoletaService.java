package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

import idat.proyecto.veterinaria.entity.Boleta;

public interface BoletaService {
	public abstract ResponseEntity<?> insert(Boleta boleta, String authorizationHeader);
	public abstract ResponseEntity<?> update(Integer id, Boleta boleta, String authorizationHeader);
	public abstract ResponseEntity<?> delete(Integer id);
	public abstract ResponseEntity<?> anular(Integer id, String mensaje);
	public abstract ResponseEntity<?> descargar(Integer id);
	public abstract ResponseEntity<?> previsualizar(Integer id);
	public abstract ResponseEntity<?> findById(Integer id);
	public abstract ResponseEntity<?> findNewId();
	public abstract ResponseEntity<?> findAll();
	public abstract ResponseEntity<?> findAllCustom();
	public abstract ResponseEntity<?> findAllMapper();
	
}
