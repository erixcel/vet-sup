package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.BoletaCustom;
import idat.proyecto.veterinaria.entity.Boleta;
import idat.proyecto.veterinaria.mapper.BoletaMapper;

public interface BoletaRepository extends JpaRepository<Boleta, Integer>{

	@Query(value="SELECT * FROM boleta_custom",nativeQuery=true)
	public abstract Collection<BoletaCustom> findAllCustom();
	
	@Query(value="SELECT * FROM boleta_mapper",nativeQuery=true)
	public abstract Collection<BoletaMapper> findAllMapper();
	
	@Query(value="SELECT IFNULL(MAX(id) + 1, 1) FROM boleta",nativeQuery=true)
	public abstract Integer findNewId();
}
