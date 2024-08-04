package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idat.proyecto.veterinaria.custom.UsuarioCustom;
import idat.proyecto.veterinaria.entity.Rol;
import idat.proyecto.veterinaria.entity.Usuario;
import idat.proyecto.veterinaria.mapper.UsuarioMapper;
import idat.proyecto.veterinaria.repository.UsuarioRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public ResponseEntity<?> insert(Usuario usuario) {
		
		ResponseEntity<?> existUsername = isExistUsername(usuario.getUsername());
		if (existUsername.getStatusCode() == HttpStatus.FOUND) return existUsername;
		
		ResponseEntity<?> existCorreo = isExistCorreo(usuario.getCorreo());
		if (existCorreo.getStatusCode() == HttpStatus.FOUND) return existCorreo;
		
		for(Rol rol : usuario.getRoles()){
			ResponseEntity<?> statusRol = rolService.findById(rol.getId());
			if (statusRol.getStatusCode() != HttpStatus.OK) return statusRol;
		}
		
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		
		usuarioRepository.save(usuario);
		return new ResponseEntity<>(Response.createMap("Usuario create!", usuario.getId()), HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Usuario usuario) {
		
		ResponseEntity<?> statusUsuario = findById(id);
		if (statusUsuario.getStatusCode() != HttpStatus.OK) return statusUsuario;
		Usuario usuarioFound = (Usuario) statusUsuario.getBody();
		
		ResponseEntity<?> existUsername = isExistUsername(usuario.getUsername());
		if (existUsername.getStatusCode() == HttpStatus.FOUND) {
			@SuppressWarnings("unchecked")
			String usernameFound = ((HashMap<String,String>) existUsername.getBody()).get("username");
			if (!usernameFound.equals(usuarioFound.getUsername())) return existUsername;
		}
		
		ResponseEntity<?> existCorreo = isExistUsername(usuario.getCorreo());
		if (existCorreo.getStatusCode() == HttpStatus.FOUND) {
			@SuppressWarnings("unchecked")
			String correoFound = ((HashMap<String,String>) existCorreo.getBody()).get("correo");
			if (!correoFound.equals(usuarioFound.getCorreo())) return existCorreo;
		}
		
		for(Rol rol : usuario.getRoles()){
			ResponseEntity<?> statusRol = rolService.findById(rol.getId());
			if (statusRol.getStatusCode() != HttpStatus.OK) return statusRol;
		}
		
		usuario.setId(id);
		usuario.setEliminado(false);
		if(!usuarioFound.getPassword().equals(usuario.getPassword())) {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		}
		usuarioRepository.save(usuario);
		return new ResponseEntity<>(Response.createMap("Usuario update!", usuario.getId()), HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusUsuario = findById(id);
		if (statusUsuario.getStatusCode() != HttpStatus.OK) return statusUsuario;
		Usuario usuarioFound = (Usuario) statusUsuario.getBody();
		usuarioFound.setEliminado(true);
		
		return new ResponseEntity<>(Response.createMap("Usuario delete!", id), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		if(usuario == null || usuario.getEliminado()) {
			return new ResponseEntity<>(Response.createMap("Usuario not found!", id), HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> isExistUsername(String username) {
		Usuario usuario = usuarioRepository.findByUsername(username);
		if(usuario != null) {
			return new ResponseEntity<>(Response.createMapUsername("Usuario exist!", username), HttpStatus.FOUND);
			
		}
		return new ResponseEntity<>(Response.createMapUsername("Usuario not exist!", username), HttpStatus.NOT_FOUND);
	}
	
	@Override
	public ResponseEntity<?> isExistCorreo(String correo) {
		Usuario usuario = usuarioRepository.findByCorreo(correo);
		if(usuario != null) {
			return new ResponseEntity<>(Response.createMapCorreo("Correo exist!", correo), HttpStatus.FOUND);
			
		}
		return new ResponseEntity<>(Response.createMapCorreo("Correo not exist!", correo), HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Usuario> coleccion = usuarioRepository.findAll().stream().filter(usuario -> !usuario.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<UsuarioCustom> coleccion = usuarioRepository.findAllCustom();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<UsuarioMapper> coleccion = usuarioRepository.findAllMapper();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

}
