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

import idat.proyecto.veterinaria.entity.BoletaProducto;
import idat.proyecto.veterinaria.service.BoletaProductoService;

@RestController
@RequestMapping("/boleta-producto")
public class BoletaProductoController {
	
    @Autowired
    private BoletaProductoService service;

    @GetMapping
    public ResponseEntity<?> findAllBoletaProducto() {
        return service.findAll();
    }
    
    @GetMapping("/custom")
	public ResponseEntity<?> findAllBoletaProductoCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllBoletaProductoMapper() {
		return service.findAllMapper();
	}

    @GetMapping("/{boletaId}/{productoId}")
    public ResponseEntity<?> findByIdBoletaProducto(@PathVariable("boletaId") Integer boletaId, @PathVariable("productoId") Integer productoId) {
        return service.findById(boletaId, productoId);
    }

    @PostMapping
    public ResponseEntity<?> insertBoletaProducto(@RequestBody BoletaProducto boletaProducto) {
        return service.insert(boletaProducto);
    }

    @PutMapping("/{boletaId}/{productoId}")
    public ResponseEntity<?> updateBoletaProducto(@PathVariable("boletaId") Integer boletaId, @PathVariable("productoId") Integer productoId, @RequestBody BoletaProducto boletaProducto) {
        return service.update(boletaId, productoId, boletaProducto);
    }

    @DeleteMapping("/{boletaId}/{productoId}")
    public ResponseEntity<?> deleteBoletaProducto(@PathVariable("boletaId") Integer boletaId, @PathVariable("productoId") Integer productoId) {
        return service.delete(boletaId, productoId);
    }
}
