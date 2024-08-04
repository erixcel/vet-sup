package idat.proyecto.veterinaria.service;

import org.springframework.http.ResponseEntity;

public interface BackupService {
	public abstract ResponseEntity<?> download();
}
