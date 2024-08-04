package idat.proyecto.veterinaria.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import idat.proyecto.veterinaria.body.IngresoBody;
import idat.proyecto.veterinaria.repository.IngresosRepository;
import idat.proyecto.veterinaria.response.Response;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class IngresoServiceImpl implements IngresoService {
	
	
	@Autowired
	private IngresosRepository ingresoRepository;

	@Override
	public ResponseEntity<?> findAllByFecha(String fecha) {
	    try {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = formatter.parse(fecha);
	        Collection<IngresoBody> coleccion = ingresoRepository.ingresos_del_dia(date);
	        if (coleccion.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<>(coleccion, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@Override
	public ResponseEntity<?> sumTotalByFecha(String fecha) {
	    try {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = formatter.parse(fecha);
	        Double total = ingresoRepository.suma_total_ingresos(date);
	        return new ResponseEntity<>(Response.createMapTotalQuery("Query to successful suma total de Ingresos!", total), HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}
