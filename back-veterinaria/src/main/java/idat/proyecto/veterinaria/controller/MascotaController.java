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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import idat.proyecto.veterinaria.entity.Mascota;
import idat.proyecto.veterinaria.service.MascotaService;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

	@Autowired
	private MascotaService service;
	
	@GetMapping
	public ResponseEntity<?> findAllMascotas() {
		return service.findAll();
	}
	
	@GetMapping("/custom")
	public ResponseEntity<?> findAllMascotasCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllMascotasMapper() {
		return service.findAllMapper();
	}
	
	@GetMapping("/cliente_id/{id}")
	public ResponseEntity<?> findAllMascotasByClienteId(@PathVariable("id") Integer id) {
		return service.findAllByClienteId(id);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdMascota(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> insertMascota(@RequestBody Mascota mascota) {
		return service.insert(mascota);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateMascota(@PathVariable("id") Integer id, @RequestBody Mascota mascota) {
		return service.update(id, mascota);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMascota(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
	
	@PostMapping("/setFoto/{id}")
    public ResponseEntity<?> saveImage(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
        return service.setFoto(id, file);
    }
}
