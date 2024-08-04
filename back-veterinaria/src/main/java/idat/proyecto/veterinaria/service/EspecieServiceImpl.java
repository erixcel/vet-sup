package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idat.proyecto.veterinaria.custom.EspecieCustom;
import idat.proyecto.veterinaria.entity.Especie;
import idat.proyecto.veterinaria.mapper.EspecieMapper;
import idat.proyecto.veterinaria.repository.EspecieRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class EspecieServiceImpl implements EspecieService{

	@Autowired
	private EspecieRepository especieRepository;

	@Override
	@Transactional
	public ResponseEntity<?> insert(Especie especie) {
		especieRepository.save(especie);
		return new ResponseEntity<>(Response.createMap("Especie create!", especie.getId()), HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Especie especie) {
		
		ResponseEntity<?> statusEspecie = findById(id);
		if (statusEspecie.getStatusCode() != HttpStatus.OK) return statusEspecie;
		
		especie.setId(id);
		especie.setEliminado(false);
		especieRepository.save(especie);
		return new ResponseEntity<>(Response.createMap("Especie update!", especie.getId()), HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusEspecie = findById(id);
		if (statusEspecie.getStatusCode() != HttpStatus.OK) return statusEspecie;
		Especie especieFound = (Especie) statusEspecie.getBody();
		especieFound.setEliminado(true);
		
		return new ResponseEntity<>(Response.createMap("Especie delete!", id), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Especie especie = especieRepository.findById(id).orElse(null);
		if(especie == null || especie.getEliminado()) {
			return new ResponseEntity<>(Response.createMap("Especie not found!", id), HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(especie, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Especie> coleccion = especieRepository.findAll().stream().filter(especie -> !especie.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<EspecieCustom> coleccion = especieRepository.findAllCustom();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<EspecieMapper> coleccion = especieRepository.findAllMapper();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

}