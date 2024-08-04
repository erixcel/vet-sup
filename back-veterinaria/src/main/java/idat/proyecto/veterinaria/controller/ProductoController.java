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

import idat.proyecto.veterinaria.entity.Producto;
import idat.proyecto.veterinaria.service.ProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private ProductoService service;
	
	@GetMapping
	public ResponseEntity<?> findAllProductos() {
		return service.findAll();
	}
	
	@GetMapping("/custom")
	public ResponseEntity<?> findAllProductosCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllProductosMapper() {
		return service.findAllMapper();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdProducto(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> insertProducto(@RequestBody Producto producto) {
		return service.insert(producto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProducto(@PathVariable("id") Integer id, @RequestBody Producto producto) {
		return service.update(id, producto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProducto(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
	
	@PostMapping("/setFoto/{id}")
    public ResponseEntity<?> saveImage(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
        return service.setFoto(id, file);
    }
	
}
