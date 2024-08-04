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
import idat.proyecto.veterinaria.custom.BanioCustom;
import idat.proyecto.veterinaria.entity.Banio;
import idat.proyecto.veterinaria.entity.Cita;
import idat.proyecto.veterinaria.enumerated.CitaEstado;
import idat.proyecto.veterinaria.mapper.BanioMapper;
import idat.proyecto.veterinaria.repository.BanioRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class BanioServiceImpl implements BanioService{

	@Autowired
	private BanioRepository banioRepository;
	
	@Autowired
	private CitaService citaService;
	
	@Autowired
	private MascotaService mascotaService;
	
	private GoogleStorage storageEntrada = new GoogleStorage("banios","vet-banio-entrada-");
	private GoogleStorage storageSalida = new GoogleStorage("banios","vet-banio-salida-");

	@Override
	@Transactional
	public ResponseEntity<?> insert(Banio banio) {
		
		ResponseEntity<?> statusMascota = mascotaService.findById(banio.getMascota().getId());
		if (statusMascota.getStatusCode() != HttpStatus.OK) return statusMascota;
		
		if (banio.getCita() != null) {
			
			ResponseEntity<?> statusCita = citaService.findById(banio.getCita().getId());
			if (statusCita.getStatusCode() != HttpStatus.OK) return statusCita;
			Cita citaFound = (Cita) statusCita.getBody();
			
			if(citaFound.getMascota().getId() != banio.getMascota().getId()) { 
				return new ResponseEntity<>("Mascota citada no coincide con el banio", HttpStatus.BAD_REQUEST);
			}
			
			if(citaFound.getEstado().equals("atendida") && citaFound.getBanio().getId() != banio.getId()) { 
				return new ResponseEntity<>("Cita ya fue atendida", HttpStatus.BAD_REQUEST);
			}
			
			if(citaFound.getEstado().equals("cancelada")) { 
				return new ResponseEntity<>("Cita esta cancelada", HttpStatus.BAD_REQUEST);
			}
			
			banio = banioRepository.save(banio);
			citaFound.setEstado(CitaEstado.atendida);
			citaFound.setFecha_atendida(banio.getFecha_creacion());
			
			return new ResponseEntity<>(Response.createMap("Banio create", banio.getId()), HttpStatus.CREATED);
		} else {
			banioRepository.save(banio);
			return new ResponseEntity<>(Response.createMap("Banio create", banio.getId()), HttpStatus.CREATED);
		}
		
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Banio banio) {
		
		ResponseEntity<?> statusBanio = findById(id);
		if (statusBanio.getStatusCode() != HttpStatus.OK) return statusBanio;
		Banio banioFound = (Banio) statusBanio.getBody();
		
		ResponseEntity<?> statusMascota = mascotaService.findById(banio.getMascota().getId());
		if (statusMascota.getStatusCode() != HttpStatus.OK) return statusMascota;
		
		banio.setId(id);
		banio.setFecha_creacion(banioFound.getFecha_creacion());
		banio.setFoto_entrada(banioFound.getFoto_entrada());
		banio.setFoto_salida(banioFound.getFoto_salida());
		banio.setEliminado(false);
		
		if (banio.getCita() != null) {
			
			ResponseEntity<?> statusCita = citaService.findById(banio.getCita().getId());
			if (statusCita.getStatusCode() != HttpStatus.OK) return statusCita;
			Cita citaFound = (Cita) statusCita.getBody();
			
			if(citaFound.getMascota().getId() != banio.getMascota().getId()) { 
				return new ResponseEntity<>("Mascota citada no coincide con el banio", HttpStatus.BAD_REQUEST);
			}
			
			if(citaFound.getEstado().equals("atendida") && citaFound.getBanio().getId() != banio.getId()) { 
				return new ResponseEntity<>("Cita ya fue atendida", HttpStatus.BAD_REQUEST);
			}
			
			if(citaFound.getEstado().equals("cancelada")) { 
				return new ResponseEntity<>("Cita esta cancelada", HttpStatus.BAD_REQUEST);
			}
			
			citaFound.setEstado(CitaEstado.atendida);
			citaFound.setFecha_atendida(banio.getFecha_creacion());
			
		} else {
			
			if (banioFound.getCita() != null) {
				Cita citaFound = banioFound.getCita();
				citaFound.setEstado(CitaEstado.pendiente);
				citaFound.setFecha_atendida(null);
			}
		}
		
		banioRepository.save(banio);
		return new ResponseEntity<>(Response.createMap("Banio update!", banio.getId()), HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusBanio = findById(id);
		if (statusBanio.getStatusCode() != HttpStatus.OK) return statusBanio;
		Banio banioFound = (Banio) statusBanio.getBody();
		banioFound.setEliminado(true);
		
		return new ResponseEntity<>(Response.createMap("Banio delete", id), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Banio banio = banioRepository.findById(id).orElse(null);
		if(banio == null || banio.getEliminado()) {
			return new ResponseEntity<>(Response.createMap("Banio not found!", id), HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(banio, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Banio> coleccion = banioRepository.findAll().stream().filter(banio -> !banio.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<BanioCustom> coleccion = banioRepository.findAllCustom();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<BanioMapper> coleccion = banioRepository.findAllMapper();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllByMascotaId(Integer mascota_id) {
		Collection<BanioMapper> coleccion = banioRepository.findAllByMascotaId(mascota_id);
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> setFotoEntrada(Integer id, MultipartFile file) {
		
		ResponseEntity<?> statusBanio = findById(id);
		if (statusBanio.getStatusCode() != HttpStatus.OK) return statusBanio;
		Banio banioFound = (Banio) statusBanio.getBody();
		
		try {
			banioFound.setFoto_entrada(storageEntrada.uploadImage(id.toString(), file));
			return new ResponseEntity<>("FotoEntrada Banio saved!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Ocurrio un error, intente nuevamente...", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> setFotoSalida(Integer id, MultipartFile file) {
		
		ResponseEntity<?> statusBanio = findById(id);
		if (statusBanio.getStatusCode() != HttpStatus.OK) return statusBanio;
		Banio banioFound = (Banio) statusBanio.getBody();
		
		try {
			banioFound.setFoto_salida(storageSalida.uploadImage(id.toString(), file));
			return new ResponseEntity<>("FotoSalida Banio saved!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Ocurrio un error, intente nuevamente...", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
