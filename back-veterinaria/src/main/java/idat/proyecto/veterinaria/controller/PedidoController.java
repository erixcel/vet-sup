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

import idat.proyecto.veterinaria.entity.Pedido;
import idat.proyecto.veterinaria.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService service;
	
	@GetMapping
	public ResponseEntity<?> findAllPedidos() {
		return service.findAll();
	}
	
	@GetMapping("/custom")
	public ResponseEntity<?> findAllPedidosCustom() {
		return service.findAllCustom();
	}
	
	@GetMapping("/mapper")
	public ResponseEntity<?> findAllPedidosMapper() {
		return service.findAllMapper();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdPedido(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@GetMapping("/new")
	public ResponseEntity<?> findNewIdPedido() {
		return service.findNewId();
	}
	
	@PostMapping
	public ResponseEntity<?> insertPedido(@RequestBody Pedido pedido, @RequestHeader("Authorization") String authorizationHeader) {
		return service.insert(pedido, authorizationHeader);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePedido(@PathVariable("id") Integer id, @RequestBody Pedido pedido, @RequestHeader("Authorization") String authorizationHeader) {
		return service.update(id, pedido, authorizationHeader);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePedido(@PathVariable("id") Integer id) {
		return service.delete(id);
	}
	
	@PutMapping("/anular/{id}")
	public ResponseEntity<?> anularPedido(@PathVariable("id") Integer id, @RequestBody String mensaje) {
		return service.anular(id, mensaje);
	}
	
	@PutMapping("/recibir/{id}")
	public ResponseEntity<?> recibirPedido(@PathVariable("id") Integer id) {
		return service.recibir(id);
	}

	@GetMapping("/descargar/{id}")
    public ResponseEntity<?> descargarPedido(@PathVariable("id") Integer id) {
		return service.descargar(id);
    }
	
	@GetMapping("/previsualizar/{id}")
    public ResponseEntity<?> previsualizarPedido(@PathVariable("id") Integer id) {
		return service.previsualizar(id);
    }
}
