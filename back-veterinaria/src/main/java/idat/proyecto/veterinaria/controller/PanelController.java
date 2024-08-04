package idat.proyecto.veterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idat.proyecto.veterinaria.service.PanelService;

@RestController
@RequestMapping("/panel")
public class PanelController {
	
	@Autowired
    private PanelService panelService;

    @GetMapping("/tratamientos")
    public ResponseEntity<?> totalTratamientos() {
        return panelService.totalTratamientos();
    }

    @GetMapping("clientes")
    public ResponseEntity<?> totalClientes() {
    	return panelService.totalClientes();
    }

    @GetMapping("/mascotas")
    public ResponseEntity<?> totalMascotas() {
    	return panelService.totalMascotas();
    }

    @GetMapping("/banios")
    public ResponseEntity<?> totalBanios() {
    	return panelService.totalBanios();
    }

    @GetMapping("/citas")
    public ResponseEntity<?> totalCitasAtendidas() {
    	return panelService.totalCitasAtendidas();
    }
    
    @GetMapping("/boletas")
    public ResponseEntity<?> totalBoletasFacturadas() {
    	return panelService.totalBoletasFacturadas();
    }

}
