package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

public interface PanelService {

	public abstract ResponseEntity<?> totalTratamientos();
	public abstract ResponseEntity<?> totalClientes();
	public abstract ResponseEntity<?> totalMascotas();
	public abstract ResponseEntity<?> totalBanios();
	public abstract ResponseEntity<?> totalCitasAtendidas();
	public abstract ResponseEntity<?> totalBoletasFacturadas();
}
