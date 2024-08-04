package idat.proyecto.veterinaria.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import idat.proyecto.veterinaria.entity.Mascota;

public interface MascotaService {
	public abstract ResponseEntity<?> insert(Mascota mascota);
	public abstract ResponseEntity<?> update(Integer id, Mascota mascota);
	public abstract ResponseEntity<?> delete(Integer id);
	public abstract ResponseEntity<?> findById(Integer id);
	public abstract ResponseEntity<?> findAll();
	public abstract ResponseEntity<?> findAllCustom();
	public abstract ResponseEntity<?> findAllMapper();
	public abstract ResponseEntity<?> findAllByClienteId(Integer cliente_id);
	public abstract ResponseEntity<?> setFoto(Integer id, MultipartFile file);
}
