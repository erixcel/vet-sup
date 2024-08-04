package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

import idat.proyecto.veterinaria.entity.Usuario;

public interface AuthService {
	
	public abstract ResponseEntity<?> authentication(Usuario usuario);
	public abstract ResponseEntity<?> verifyToken(String authorizationHeader);
	public abstract ResponseEntity<?> getUsuario(String authorizationHeader);
}
