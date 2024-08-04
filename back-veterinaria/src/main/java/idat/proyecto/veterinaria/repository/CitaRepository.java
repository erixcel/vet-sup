package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.CitaCustom;
import idat.proyecto.veterinaria.entity.Cita;
import idat.proyecto.veterinaria.mapper.CitaMapper;

public interface CitaRepository extends JpaRepository<Cita, Integer>{

	@Query(value="SELECT * FROM cita_custom",nativeQuery=true)
	public abstract Collection<CitaCustom> findAllCustom();
	
	@Query(value="SELECT * FROM cita_mapper",nativeQuery=true)
	public abstract Collection<CitaMapper> findAllMapper();
	
	@Query(value="CALL get_citas_by_mascota_id(?)",nativeQuery=true)
	public abstract Collection<CitaMapper> findAllByMascotaId(Integer mascota_id);
	
	@Query(value="CALL get_citas_by_estado(?)",nativeQuery=true)
	public abstract Collection<CitaMapper> findAllByEstado(String estado);
}
