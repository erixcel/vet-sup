package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import idat.proyecto.veterinaria.entity.Banio;

public interface BanioService {
	public abstract ResponseEntity<?> insert(Banio banio);
	public abstract ResponseEntity<?> update(Integer id, Banio banio);
	public abstract ResponseEntity<?> delete(Integer id);
	public abstract ResponseEntity<?> findById(Integer id);
	public abstract ResponseEntity<?> findAll();
	public abstract ResponseEntity<?> findAllCustom();
	public abstract ResponseEntity<?> findAllMapper();
	public abstract ResponseEntity<?> findAllByMascotaId(Integer mascota_id);
	public abstract ResponseEntity<?> setFotoEntrada(Integer id, MultipartFile file);
	public abstract ResponseEntity<?> setFotoSalida(Integer id, MultipartFile file);
}
