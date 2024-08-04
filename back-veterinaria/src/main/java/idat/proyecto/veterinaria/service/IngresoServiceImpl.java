package idat.proyecto.veterinaria.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import idat.proyecto.veterinaria.body.IngresoBody;
import idat.proyecto.veterinaria.repository.IngresosRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class IngresoServiceImpl implements IngresoService {
	
	@Autowired
	private IngresosRepository ingresoRepository;

	@Override
	public ResponseEntity<?> findAllByFecha(String fecha) {
		Collection<IngresoBody> coleccion = ingresoRepository.ingresos_del_dia(fecha);
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> sumTotalByFecha(String fecha) {
		Double total = ingresoRepository.suma_total_ingresos(fecha);
    	return new ResponseEntity<>(Response.createMapTotalQuery("Query to successful suma total de Ingresos!", total), HttpStatus.OK);

	}

}
