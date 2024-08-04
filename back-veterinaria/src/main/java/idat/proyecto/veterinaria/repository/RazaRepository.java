package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.RazaCustom;
import idat.proyecto.veterinaria.entity.Raza;
import idat.proyecto.veterinaria.mapper.RazaMapper;

public interface RazaRepository extends JpaRepository<Raza, Integer>{

	@Query(value="SELECT * FROM raza_custom",nativeQuery=true)
	public abstract Collection<RazaCustom> findAllCustom();
	
	@Query(value="SELECT * FROM raza_mapper",nativeQuery=true)
	public abstract Collection<RazaMapper> findAllMapper();
}
