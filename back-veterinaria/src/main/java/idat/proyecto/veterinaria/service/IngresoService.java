package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

public interface IngresoService {
	
	public abstract ResponseEntity<?> findAllByFecha(String fecha);
	public abstract ResponseEntity<?> sumTotalByFecha(String fecha);
}
