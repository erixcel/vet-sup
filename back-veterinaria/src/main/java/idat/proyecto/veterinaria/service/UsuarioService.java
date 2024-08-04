package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

import idat.proyecto.veterinaria.entity.Usuario;

public interface UsuarioService {
	
	public abstract ResponseEntity<?> insert(Usuario usuario);
	public abstract ResponseEntity<?> update(Integer id, Usuario usuario);
	public abstract ResponseEntity<?> delete(Integer id);
	public abstract ResponseEntity<?> findById(Integer id);
	public abstract ResponseEntity<?> isExistUsername(String username);
	public abstract ResponseEntity<?> isExistCorreo(String correo);
	public abstract ResponseEntity<?> findAll();
	public abstract ResponseEntity<?> findAllCustom();
	public abstract ResponseEntity<?> findAllMapper();
}
