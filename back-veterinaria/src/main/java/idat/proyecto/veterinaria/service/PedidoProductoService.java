package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

import idat.proyecto.veterinaria.entity.PedidoProducto;

public interface PedidoProductoService {

	public abstract ResponseEntity<?> insert(PedidoProducto pedidoProducto);
    public abstract ResponseEntity<?> update(Integer pedidoId, Integer productoId, PedidoProducto pedidoProducto);
    public abstract ResponseEntity<?> delete(Integer pedidoId, Integer productoId);
    public abstract ResponseEntity<?> findById(Integer pedidoId, Integer productoId);
    public abstract ResponseEntity<?> findAll();
    public abstract ResponseEntity<?> findAllCustom();
	public abstract ResponseEntity<?> findAllMapper();
}
