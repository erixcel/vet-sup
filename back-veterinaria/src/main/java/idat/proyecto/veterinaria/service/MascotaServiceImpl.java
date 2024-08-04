package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import idat.proyecto.veterinaria.cloud.GoogleStorage;
import idat.proyecto.veterinaria.custom.MascotaCustom;
import idat.proyecto.veterinaria.entity.Mascota;
import idat.proyecto.veterinaria.mapper.MascotaMapper;
import idat.proyecto.veterinaria.repository.MascotaRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class MascotaServiceImpl implements MascotaService{

	@Autowired
	private MascotaRepository mascotaRepository;
	
	@Autowired
	private RazaService razaService;
	
	@Autowired
	private ClienteService clienteService;
	
	private GoogleStorage storage = new GoogleStorage("mascotas","vet-mascota-");

	@Override
	@Transactional
	public ResponseEntity<?> insert(Mascota mascota) {
		
		ResponseEntity<?> statusRaza = razaService.findById(mascota.getRaza().getId());
		if (statusRaza.getStatusCode() != HttpStatus.OK) return statusRaza;
		
		ResponseEntity<?> statusCliente = clienteService.findById(mascota.getCliente().getId());
		if (statusCliente.getStatusCode() != HttpStatus.OK) return statusCliente;
		
		mascotaRepository.save(mascota);
		return new ResponseEntity<>(Response.createMap("Mascota create!", mascota.getId()), HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Mascota mascota) {
		
		ResponseEntity<?> statusMascota = findById(id);
		if (statusMascota.getStatusCode() != HttpStatus.OK) return statusMascota;
		Mascota mascotaFound = (Mascota) statusMascota.getBody();
		
		ResponseEntity<?> statusRaza = razaService.findById(mascota.getRaza().getId());
		if (statusRaza.getStatusCode() != HttpStatus.OK) return statusRaza;
		
		ResponseEntity<?> statusCliente = clienteService.findById(mascota.getCliente().getId());
		if (statusCliente.getStatusCode() != HttpStatus.OK) return statusCliente;
		
		mascota.setId(id);
		mascota.setFecha_creacion(mascotaFound.getFecha_creacion());
		mascota.setFoto(mascotaFound.getFoto());
		mascota.setEliminado(false);
		mascotaRepository.save(mascota);
		return new ResponseEntity<>(Response.createMap("Mascota update!", mascota.getId()), HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusMascota = findById(id);
		if (statusMascota.getStatusCode() != HttpStatus.OK) return statusMascota;
		Mascota mascotaFound = (Mascota) statusMascota.getBody();
		mascotaFound.setEliminado(true);
		
		return new ResponseEntity<>(Response.createMap("Mascota delete!", id), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Mascota mascota = mascotaRepository.findById(id).orElse(null);
		if(mascota == null || mascota.getEliminado()) {
			return new ResponseEntity<>(Response.createMap("Mascota not found!", id), HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(mascota, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Mascota> coleccion = mascotaRepository.findAll().stream().filter(mascota -> !mascota.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<MascotaCustom> coleccion = mascotaRepository.findAllCustom();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<MascotaMapper> coleccion = mascotaRepository.findAllMapper();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllByClienteId(Integer cliente_id) {
		Collection<MascotaMapper> coleccion = mascotaRepository.findAllMascotasByClienteId(cliente_id);
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> setFoto(Integer id, MultipartFile file) {
		
		ResponseEntity<?> statusMascota = findById(id);
		if (statusMascota.getStatusCode() != HttpStatus.OK) return statusMascota;
		Mascota mascotaFound = (Mascota) statusMascota.getBody();
		
		try {
			mascotaFound.setFoto(storage.uploadImage(id.toString(), file));
			return new ResponseEntity<>("Foto Mascota saved!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Ocurrio un error, intente nuevamente...", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}