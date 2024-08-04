package idat.proyecto.veterinaria.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import idat.proyecto.veterinaria.response.Response;

@Service
public class BackupServiceImpl implements BackupService{

	@Override
	public ResponseEntity<?> download() {
	    
	    String dbName = "postgres";
	    String dbUser = "postgres";
	    String host = "your_host";
	    String port = "5432";

	    String pgDumpPath = "C:\\Program Files\\PostgreSQL\\13\\bin\\pg_dump.exe"; // Ajusta esta ruta según tu sistema
	    String fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

	    try {
	        ProcessBuilder processBuilder = new ProcessBuilder(pgDumpPath, "-h", host, "-p", port, "-U", dbUser, "-f", "backup.sql", dbName);
	        File tempFile = File.createTempFile("backup", ".sql");
	        processBuilder.redirectOutput(tempFile);
	        processBuilder.start().waitFor();

	        Path path = tempFile.toPath();
	        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=backup " + fecha + ".sql")
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .body(resource);
	    } catch (IOException | InterruptedException e) {
	        System.out.println(e);
	        return new ResponseEntity<>(Response.createMapError("Error al descargar el backup " + fecha + "!", fecha), HttpStatus.BAD_REQUEST);
	    }
	}




}
