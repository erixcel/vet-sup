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

import idat.proyecto.veterinaria.entity.Raza;
import idat.proyecto.veterinaria.service.RazaService;

@RestController
@RequestMapping("/razas")
public class RazaController {

	@Autowired
	private RazaService service;
	
	@GetMapping
	public ResponseEntity<?> findAllRazas() {
		return service.findAll();
	}
	
	@GetMapping("/custom")
	public ResponseEntity<?> findAllRazasCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllRazasMapper() {
		return service.findAllMapper();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdRaza(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> insertRaza(@RequestBody Raza raza) {
		return service.insert(raza);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateRaza(@PathVariable("id") Integer id, @RequestBody Raza raza) {
		return service.update(id, raza);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRaza(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
	
}
