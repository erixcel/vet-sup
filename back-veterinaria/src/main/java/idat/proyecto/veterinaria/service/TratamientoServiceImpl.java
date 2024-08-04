package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idat.proyecto.veterinaria.custom.TratamientoCustom;
import idat.proyecto.veterinaria.entity.Cita;
import idat.proyecto.veterinaria.entity.Tratamiento;
import idat.proyecto.veterinaria.enumerated.CitaEstado;
import idat.proyecto.veterinaria.mapper.TratamientoMapper;
import idat.proyecto.veterinaria.repository.TratamientoRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class TratamientoServiceImpl implements TratamientoService{

	@Autowired
	private TratamientoRepository tratamientoRepository;
	
	@Autowired
	private CitaService citaService;
	
	@Autowired
	private MascotaService mascotaService;

	@Override
	@Transactional
	public ResponseEntity<?> insert(Tratamiento tratamiento) {
		
		ResponseEntity<?> statusMascota = mascotaService.findById(tratamiento.getMascota().getId());
		if (statusMascota.getStatusCode() != HttpStatus.OK) return statusMascota;
		
		if (tratamiento.getCita() != null) {
			
			ResponseEntity<?> statusCita = citaService.findById(tratamiento.getCita().getId());
			if (statusCita.getStatusCode() != HttpStatus.OK) return statusCita;
			Cita citaFound = (Cita) statusCita.getBody();
			
			if(citaFound.getMascota().getId() != tratamiento.getMascota().getId()) { 
				return new ResponseEntity<>("Mascota citada no coincide con el tratamiento", HttpStatus.BAD_REQUEST);
			}
			
			if(citaFound.getEstado().equals("atendida")) { 
				return new ResponseEntity<>("Cita ya fue atendida", HttpStatus.BAD_REQUEST);
			}
			
			if(citaFound.getEstado().equals("cancelada")) { 
				return new ResponseEntity<>("Cita esta cancelada", HttpStatus.BAD_REQUEST);
			}
			
			tratamiento = tratamientoRepository.save(tratamiento);
			citaFound.setEstado(CitaEstado.atendida);
			citaFound.setFecha_atendida(tratamiento.getFecha_creacion());
			
			return new ResponseEntity<>(Response.createMap("Tratamiento create!", tratamiento.getId()), HttpStatus.CREATED);
		} else {
			tratamientoRepository.save(tratamiento);
			return new ResponseEntity<>(Response.createMap("Tratamiento create!", tratamiento.getId()), HttpStatus.CREATED);
		}
		
	}

	@Override
	@Transactional
	public ResponseEntity<?> update(Integer id, Tratamiento tratamiento) {
		
		ResponseEntity<?> statusTratamiento = findById(id);
		if (statusTratamiento.getStatusCode() != HttpStatus.OK) return statusTratamiento;
		Tratamiento tratamientoFound = (Tratamiento) statusTratamiento.getBody();
		
		ResponseEntity<?> statusMascota = mascotaService.findById(tratamiento.getMascota().getId());
		if (statusMascota.getStatusCode() != HttpStatus.OK) return statusMascota;
		
		tratamiento.setId(id);
		tratamiento.setFecha_creacion(tratamientoFound.getFecha_creacion());
		tratamiento.setEliminado(false);
		
		if (tratamiento.getCita() != null) {
			
			ResponseEntity<?> statusCita = citaService.findById(tratamiento.getCita().getId());
			if (statusCita.getStatusCode() != HttpStatus.OK) return statusCita;
			Cita citaFound = (Cita) statusCita.getBody();
			
			if(citaFound.getMascota().getId() != tratamiento.getMascota().getId()) { 
				return new ResponseEntity<>("Mascota citada no coincide con el tratamiento", HttpStatus.BAD_REQUEST);
			}
			
			if(citaFound.getEstado().equals("atendida") && citaFound.getTratamiento().getId() != tratamiento.getId()) { 
				return new ResponseEntity<>("Cita ya fue atendida", HttpStatus.BAD_REQUEST);
			}
			
			if(citaFound.getEstado().equals("cancelada")) { 
				return new ResponseEntity<>("Cita esta cancelada", HttpStatus.BAD_REQUEST);
			}
			
			citaFound.setEstado(CitaEstado.atendida);
			citaFound.setFecha_atendida(tratamiento.getFecha_creacion());
			
		} else {
			
			if (tratamientoFound.getCita() != null) {
				Cita citaFound = tratamientoFound.getCita();
				citaFound.setEstado(CitaEstado.pendiente);
				citaFound.setFecha_atendida(null);
			}
		}

		tratamientoRepository.save(tratamiento);
		return new ResponseEntity<>(Response.createMap("Tratamiento update!", tratamiento.getId()), HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Integer id) {
		
		ResponseEntity<?> statusTratamiento = findById(id);
		if (statusTratamiento.getStatusCode() != HttpStatus.OK) return statusTratamiento;
		Tratamiento tratamientoFound = (Tratamiento) statusTratamiento.getBody();
		tratamientoFound.setEliminado(true);
		
		return new ResponseEntity<>(Response.createMap("Tratamiento delete!", id), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		Tratamiento tratamiento = tratamientoRepository.findById(id).orElse(null);
		if(tratamiento == null || tratamiento.getEliminado()) {
			return new ResponseEntity<>(Response.createMap("Tratamiento not found!", id), HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(tratamiento, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAll() {
		Collection<Tratamiento> coleccion = tratamientoRepository.findAll().stream().filter(tratamiento -> !tratamiento.getEliminado()).collect(Collectors.toList());
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<TratamientoCustom> coleccion = tratamientoRepository.findAllCustom();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<TratamientoMapper> coleccion = tratamientoRepository.findAllMapper();
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAllByMascotaId(Integer mascota_id) {
		Collection<TratamientoMapper> coleccion = tratamientoRepository.findAllByMascotaId(mascota_id);
		if (coleccion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

}