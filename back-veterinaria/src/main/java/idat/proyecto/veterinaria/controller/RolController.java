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

import idat.proyecto.veterinaria.entity.Rol;
import idat.proyecto.veterinaria.service.RolService;

@RestController
@RequestMapping("/roles")
public class RolController {
	
	@Autowired
	private RolService service;
	
	@GetMapping
	public ResponseEntity<?> findAllRoles() {
		return service.findAll();
	}
	
	@GetMapping("/custom")
	public ResponseEntity<?> findAllRolesCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllRolesMapper() {
		return service.findAllMapper();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdRol(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> insertRol(@RequestBody Rol rol) {
		return service.insert(rol);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateRol(@PathVariable("id") Integer id, @RequestBody Rol rol) {
		return service.update(id, rol);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRol(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
}
