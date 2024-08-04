package idat.proyecto.veterinaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import idat.proyecto.veterinaria.repository.PanelRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class PanelServiceImpl implements PanelService{

	@Autowired
    private PanelRepository panelRepository;

    @Override
    public ResponseEntity<?> totalTratamientos() {
    	Integer total = panelRepository.totalTratamientos();
    	return new ResponseEntity<>(Response.createMapTotalQuery("Query to successful tratamientos!", total), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> totalClientes() {
    	Integer total = panelRepository.totalClientes();
    	return new ResponseEntity<>(Response.createMapTotalQuery("Query to successful clientes!", total), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> totalMascotas() {
    	Integer total = panelRepository.totalMascotas();
    	return new ResponseEntity<>(Response.createMapTotalQuery("Query to successful mascotas!", total), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> totalBanios() {
    	Integer total = panelRepository.totalBanios();
    	return new ResponseEntity<>(Response.createMapTotalQuery("Query to successful banios!", total), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> totalCitasAtendidas() {
    	Integer total = panelRepository.totalCitasAtendidas();
    	return new ResponseEntity<>(Response.createMapTotalQuery("Query to successful citas!", total), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> totalBoletasFacturadas() {
    	Integer total = panelRepository.totalBoletasFacturadas();
    	return new ResponseEntity<>(Response.createMapTotalQuery("Query to successful boletas!", total), HttpStatus.OK);
    }
}
