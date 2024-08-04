package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import idat.proyecto.veterinaria.custom.RolCustom;
import idat.proyecto.veterinaria.entity.Rol;
import idat.proyecto.veterinaria.mapper.RolMapper;
import idat.proyecto.veterinaria.repository.RolRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class RolServiceImpl implements RolService{
	
	@Autowired
	private RolRepository rolRepository;

	@Override
	@Transactional
	public ResponseEntity<?> insert(Rol rol) {
		rolRepository.save(rol);
		return new ResponseEntity<>(Response.createMap("Rol create!", rol.getId()), HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Rol rol) {
		
		ResponseEntity<?> statusRol = findById(id);
		if (statusRol.getStatusCode() != HttpStatus.OK) return statusRol;
		
		rol.setId(id);
		rol.setEliminado(false);
		rolRepository.save(rol);
		return new ResponseEntity<>(Response.createMap("Rol update!", rol.getId()), HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusRol = findById(id);
		if (statusRol.getStatusCode() != HttpStatus.OK) return statusRol;
		Rol rolFound = (Rol) statusRol.getBody();
		rolFound.setEliminado(true);
		
		return new ResponseEntity<>(Response.createMap("Rol delete!", id), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Rol rol = rolRepository.findById(id).orElse(null);
		if(rol == null || rol.getEliminado()) {
			return new ResponseEntity<>(Response.createMap("Rol not found!", id), HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(rol, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Rol> coleccion = rolRepository.findAll().stream().filter(rol -> !rol.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<RolCustom> coleccion = rolRepository.findAllCustom();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<RolMapper> coleccion = rolRepository.findAllMapper();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

}
