package idat.proyecto.veterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idat.proyecto.veterinaria.service.IngresoService;

@RestController
@RequestMapping("/ingresos")
public class IngresosController {
	
	@Autowired
	private IngresoService service;
	
	@GetMapping("/{fecha}")
	public ResponseEntity<?> findAllByFecha(@PathVariable("fecha") String fecha) {
		return service.findAllByFecha(fecha);
	}
	
	@GetMapping("/suma/{fecha}")
	public ResponseEntity<?> sumTotalByFecha(@PathVariable("fecha") String fecha) {
		return service.sumTotalByFecha(fecha);
	}
}
