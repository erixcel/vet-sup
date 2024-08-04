package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

import idat.proyecto.veterinaria.entity.BoletaProducto;

public interface BoletaProductoService {
	
    public abstract ResponseEntity<?> insert(BoletaProducto boletaProducto);
    public abstract ResponseEntity<?> update(Integer boletaId, Integer productoId, BoletaProducto boletaProducto);
    public abstract ResponseEntity<?> delete(Integer boletaId, Integer productoId);
    public abstract ResponseEntity<?> findById(Integer boletaId, Integer productoId);
    public abstract ResponseEntity<?> findAll();
    public abstract ResponseEntity<?> findAllCustom();
	public abstract ResponseEntity<?> findAllMapper();
}
