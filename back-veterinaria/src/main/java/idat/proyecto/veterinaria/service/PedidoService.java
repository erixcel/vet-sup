package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

import idat.proyecto.veterinaria.entity.Pedido;

public interface PedidoService {
	public abstract ResponseEntity<?> insert(Pedido pedido, String authorizationHeader);
	public abstract ResponseEntity<?> update(Integer id, Pedido pedido, String authorizationHeader);
	public abstract ResponseEntity<?> delete(Integer id);
	public abstract ResponseEntity<?> anular(Integer id, String mensaje);
	public abstract ResponseEntity<?> recibir(Integer id);
	public abstract ResponseEntity<?> descargar(Integer id);
	public abstract ResponseEntity<?> previsualizar(Integer id);
	public abstract ResponseEntity<?> findById(Integer id);
	public abstract ResponseEntity<?> findNewId();
	public abstract ResponseEntity<?> findAll();
	public abstract ResponseEntity<?> findAllCustom();
	public abstract ResponseEntity<?> findAllMapper();
}
