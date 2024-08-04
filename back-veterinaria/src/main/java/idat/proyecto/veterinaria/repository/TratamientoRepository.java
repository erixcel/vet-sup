package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.TratamientoCustom;
import idat.proyecto.veterinaria.entity.Tratamiento;
import idat.proyecto.veterinaria.mapper.TratamientoMapper;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Integer>{

	@Query(value="SELECT * FROM tratamiento_custom",nativeQuery=true)
	public abstract Collection<TratamientoCustom> findAllCustom();
	
	@Query(value="SELECT * FROM tratamiento_mapper",nativeQuery=true)
	public abstract Collection<TratamientoMapper> findAllMapper();
	
	@Query(value="CALL get_tratamientos_by_mascota_id(?)",nativeQuery=true)
	public abstract Collection<TratamientoMapper> findAllByMascotaId(Integer mascota_id);
}
