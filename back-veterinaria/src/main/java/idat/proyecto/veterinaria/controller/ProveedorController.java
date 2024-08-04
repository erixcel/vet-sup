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

import idat.proyecto.veterinaria.entity.Proveedor;
import idat.proyecto.veterinaria.service.ProveedorService;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {
	
	@Autowired
	private ProveedorService service;
	
	@GetMapping
	public ResponseEntity<?> findAllProveedores() {
		return service.findAll();
	}
	
	@GetMapping("/custom")
	public ResponseEntity<?> findAllProveedoresCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllProveedoresMapper() {
		return service.findAllMapper();
	}
	
	@GetMapping("/productos/{id}")
	public ResponseEntity<?> findProductosByIdProveedor(@PathVariable("id") Integer id) {
		return service.findProductosById(id);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdProveedor(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> insertProveedor(@RequestBody Proveedor proveedor) {
		return service.insert(proveedor);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProveedor(@PathVariable("id") Integer id, @RequestBody Proveedor proveedor) {
		return service.update(id, proveedor);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProveedor(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
	
	@PostMapping("/setFoto/{id}")
    public ResponseEntity<?> saveImage(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
        return service.setFoto(id, file);
    }
}
