package idat.proyecto.veterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import idat.proyecto.veterinaria.entity.Usuario;
import idat.proyecto.veterinaria.service.AuthService;

@RestController
public class AuthController {

	@Autowired
	private AuthService service;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        return service.authentication(usuario);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authorizationHeader) {
        return service.verifyToken(authorizationHeader);
    }
    
    @GetMapping("/usuario")
    public ResponseEntity<?> getUsuario(@RequestHeader("Authorization") String authorizationHeader) {
        return service.getUsuario(authorizationHeader);
    }
}

