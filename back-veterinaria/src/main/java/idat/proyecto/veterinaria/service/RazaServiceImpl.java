package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idat.proyecto.veterinaria.custom.RazaCustom;
import idat.proyecto.veterinaria.entity.Raza;
import idat.proyecto.veterinaria.mapper.RazaMapper;
import idat.proyecto.veterinaria.repository.RazaRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class RazaServiceImpl implements RazaService{

	@Autowired
	private RazaRepository razaRepository;

	@Autowired
	private EspecieService especieService;
	
	@Override
	@Transactional
	public ResponseEntity<?> insert(Raza raza) {
		
		ResponseEntity<?> statusEspecie = especieService.findById(raza.getEspecie().getId());
		if (statusEspecie.getStatusCode() != HttpStatus.OK) return statusEspecie;
		
		razaRepository.save(raza);
		return new ResponseEntity<>(Response.createMap("Raza create!", raza.getId()), HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Raza raza) {
		
		ResponseEntity<?> statusRaza = findById(id);
		if (statusRaza.getStatusCode() != HttpStatus.OK) return statusRaza;
		
		ResponseEntity<?> statusEspecie = especieService.findById(raza.getEspecie().getId());
		if (statusEspecie.getStatusCode() != HttpStatus.OK) return statusEspecie;
		
		raza.setId(id);
		raza.setEliminado(false);
		razaRepository.save(raza);
		return new ResponseEntity<>(Response.createMap("Raza update!", raza.getId()), HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusRaza = findById(id);
		if (statusRaza.getStatusCode() != HttpStatus.OK) return statusRaza;
		Raza razaFound = (Raza) statusRaza.getBody();
		razaFound.setEliminado(true);
		
		return new ResponseEntity<>(Response.createMap("Raza delete!", id), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Raza raza = razaRepository.findById(id).orElse(null);
		if(raza == null || raza.getEliminado()) {
			return new ResponseEntity<>(Response.createMap("Raza not found!", id), HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(raza, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Raza> coleccion = razaRepository.findAll().stream().filter(raza -> !raza.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<RazaCustom> coleccion = razaRepository.findAllCustom();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<RazaMapper> coleccion = razaRepository.findAllMapper();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

}