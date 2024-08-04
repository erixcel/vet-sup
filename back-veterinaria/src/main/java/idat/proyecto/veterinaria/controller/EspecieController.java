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

import idat.proyecto.veterinaria.entity.Especie;
import idat.proyecto.veterinaria.service.EspecieService;

@RestController
@RequestMapping("/especies")
public class EspecieController {
	
	@Autowired
	private EspecieService service;
	
	@GetMapping
	public ResponseEntity<?> findAllEspecies() {
		return service.findAll();
	}
	
	@GetMapping("/custom")
	public ResponseEntity<?> findAllEspeciesCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllEspeciesMapper() {
		return service.findAllMapper();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdEspecie(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> insertEspecie(@RequestBody Especie especie) {
		return service.insert(especie);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEspecie(@PathVariable("id") Integer id, @RequestBody Especie especie) {
		return service.update(id, especie);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEspecie(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
}
