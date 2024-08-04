package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.MascotaCustom;
import idat.proyecto.veterinaria.entity.Mascota;
import idat.proyecto.veterinaria.mapper.MascotaMapper;

public interface MascotaRepository extends JpaRepository<Mascota, Integer>{
	
	@Query(value="SELECT * FROM mascota_custom",nativeQuery=true)
	public abstract Collection<MascotaCustom> findAllCustom();
	
	@Query(value="SELECT * FROM mascota_mapper",nativeQuery=true)
	public abstract Collection<MascotaMapper> findAllMapper();
	
	@Query(value="CALL get_mascotas_by_cliente_id(?)",nativeQuery=true)
	public abstract Collection<MascotaMapper> findAllMascotasByClienteId(Integer cliente_id);
	

}
