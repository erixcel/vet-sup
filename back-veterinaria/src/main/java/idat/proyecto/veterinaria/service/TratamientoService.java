package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

import idat.proyecto.veterinaria.entity.Tratamiento;

public interface TratamientoService {
	public abstract ResponseEntity<?> insert(Tratamiento tratamiento);
	public abstract ResponseEntity<?> update(Integer id, Tratamiento tratamiento);
	public abstract ResponseEntity<?> delete(Integer id);
	public abstract ResponseEntity<?> findById(Integer id);
	public abstract ResponseEntity<?> findAll();
	public abstract ResponseEntity<?> findAllCustom();
	public abstract ResponseEntity<?> findAllByMascotaId(Integer mascota_id);
	public abstract ResponseEntity<?> findAllMapper();
}
