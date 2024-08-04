package idat.proyecto.veterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idat.proyecto.veterinaria.entity.Usuario;
import idat.proyecto.veterinaria.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping
	public ResponseEntity<?> findAllUsuarios() {
		return service.findAll();
	}
	
	@GetMapping("/custom")
	public ResponseEntity<?> findAllUsuariosCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllUsuariosMapper() {
		return service.findAllMapper();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdUsuario(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> insertUsuario(@RequestBody Usuario usuario) {
		return service.insert(usuario);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUsuario(@PathVariable("id") Integer id, @RequestBody Usuario usuario) {
		return service.update(id, usuario);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUsuario(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
}
