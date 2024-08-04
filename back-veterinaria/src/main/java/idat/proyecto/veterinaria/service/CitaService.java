package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

import idat.proyecto.veterinaria.entity.Cita;

public interface CitaService {

	public abstract ResponseEntity<?> insert(Cita cita);
	public abstract ResponseEntity<?> update(Integer id, Cita cita);
	public abstract ResponseEntity<?> delete(Integer id);
	public abstract ResponseEntity<?> cancelar(Integer id, String mensaje);
	public abstract ResponseEntity<?> findById(Integer id);
	public abstract ResponseEntity<?> findAll();
	public abstract ResponseEntity<?> findAllCustom();
	public abstract ResponseEntity<?> findAllMapper();
	public abstract ResponseEntity<?> findAllByMascotaId(Integer mascota_id);
	public abstract ResponseEntity<?> findAllByEstado(String estado);
}
