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

import idat.proyecto.veterinaria.entity.Cliente;
import idat.proyecto.veterinaria.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	@GetMapping
	public ResponseEntity<?> findAllClientes() {
		return service.findAll();
	}
	
	@GetMapping("/custom")
	public ResponseEntity<?> findAllClientesCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllClientesMapper() {
		return service.findAllMapper();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdCliente(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> insertCliente(@RequestBody Cliente cliente) {
		return service.insert(cliente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCliente(@PathVariable("id") Integer id, @RequestBody Cliente cliente) {
		return service.update(id, cliente);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCliente(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
	
	@PostMapping("/setFoto/{id}")
    public ResponseEntity<?> saveImage(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
        return service.setFoto(id, file);
    }
	
}
