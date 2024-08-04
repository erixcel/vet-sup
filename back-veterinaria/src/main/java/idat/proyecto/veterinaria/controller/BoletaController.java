package idat.proyecto.veterinaria.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idat.proyecto.veterinaria.entity.Boleta;
import idat.proyecto.veterinaria.service.BoletaService;

@RestController
@RequestMapping("/boletas")
public class BoletaController {
	
	@Autowired
	private BoletaService service;
	
	@GetMapping
	public ResponseEntity<?> findAllBoletas() {
		return service.findAll();
	}
	
	@GetMapping("/custom")
	public ResponseEntity<?> findAllBoletasCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllBoletasMapper() {
		return service.findAllMapper();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdBoleta(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@GetMapping("/new")
	public ResponseEntity<?> findNewIdBoleta() {
		return service.findNewId();
	}
	
	@PostMapping
	public ResponseEntity<?> insertBoleta(@RequestBody Boleta boleta, @RequestHeader("Authorization") String authorizationHeader) {
		return service.insert(boleta, authorizationHeader);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBoleta(@PathVariable("id") Integer id, @RequestBody Boleta boleta, @RequestHeader("Authorization") String authorizationHeader) {
		return service.update(id, boleta, authorizationHeader);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBoleta(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
	
	@PutMapping("/anular/{id}")
	public ResponseEntity<?> anularBoleta(@PathVariable("id") Integer id, @RequestBody String mensaje) {
		return service.anular(id, mensaje);
	}

	@GetMapping("/descargar/{id}")
    public ResponseEntity<?> descargarBoleta(@PathVariable("id") Integer id) {
		return service.descargar(id);
    }
	
	@GetMapping("/previsualizar/{id}")
    public ResponseEntity<?> previsualizarBoleta(@PathVariable("id") Integer id) {
		return service.previsualizar(id);
    }
}
