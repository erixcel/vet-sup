package idat.proyecto.veterinaria.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idat.proyecto.veterinaria.service.BackupService;


@RestController
@RequestMapping("/backup")
public class BackupController {
	
	@Autowired
	private BackupService service;
    
    @GetMapping("/download")
    public ResponseEntity<?> downloadRespaldo() {
        return service.download();
    }

}
