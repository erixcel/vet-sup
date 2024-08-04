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

import idat.proyecto.veterinaria.entity.Tratamiento;
import idat.proyecto.veterinaria.service.TratamientoService;

@RestController
@RequestMapping("/tratamientos")
public class TratamientoController {
	
	@Autowired
	private TratamientoService service;
	
	@GetMapping
	public ResponseEntity<?> findAllTratamientos() {
		return service.findAll();
	}
	
	@GetMapping("/custom")
	public ResponseEntity<?> findAllTratamientosCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllTratamientosMapper() {
		return service.findAllMapper();
	}
	
	@GetMapping("/mascota_id/{id}")
	public ResponseEntity<?> findAllTratamientosByMascotaId(@PathVariable("id") Integer id) {
		return service.findAllByMascotaId(id);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdTratamiento(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> insertTratamiento(@RequestBody Tratamiento tratamiento) {
		return service.insert(tratamiento);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateTratamiento(@PathVariable("id") Integer id, @RequestBody Tratamiento tratamiento) {
		return service.update(id, tratamiento);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTratamiento(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
	
}
