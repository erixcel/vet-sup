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

import idat.proyecto.veterinaria.entity.PedidoProducto;
import idat.proyecto.veterinaria.service.PedidoProductoService;

@RestController
@RequestMapping("/pedido-producto")
public class PedidoProductoController {
	
    @Autowired
    private PedidoProductoService service;

    @GetMapping
    public ResponseEntity<?> findAllPedidoProducto() {
        return service.findAll();
    }
    
    @GetMapping("/custom")
	public ResponseEntity<?> findAllPedidoProductoCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllPedidoProductoMapper() {
		return service.findAllMapper();
	}

    @GetMapping("/{pedidoId}/{productoId}")
    public ResponseEntity<?> findByIdPedidoProducto(@PathVariable("pedidoId") Integer pedidoId, @PathVariable("productoId") Integer productoId) {
        return service.findById(pedidoId, productoId);
    }

    @PostMapping
    public ResponseEntity<?> insertPedidoProducto(@RequestBody PedidoProducto pedidoProducto) {
        return service.insert(pedidoProducto);
    }

    @PutMapping("/{pedidoId}/{productoId}")
    public ResponseEntity<?> updatePedidoProducto(@PathVariable("pedidoId") Integer pedidoId, @PathVariable("productoId") Integer productoId, @RequestBody PedidoProducto pedidoProducto) {
        return service.update(pedidoId, productoId, pedidoProducto);
    }

    @DeleteMapping("/{pedidoId}/{productoId}")
    public ResponseEntity<?> deletePedidoProducto(@PathVariable("pedidoId") Integer pedidoId, @PathVariable("productoId") Integer productoId) {
        return service.delete(pedidoId, productoId);
    }
}
