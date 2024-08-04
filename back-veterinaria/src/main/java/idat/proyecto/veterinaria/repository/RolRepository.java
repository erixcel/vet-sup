package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.RolCustom;
import idat.proyecto.veterinaria.entity.Rol;
import idat.proyecto.veterinaria.mapper.RolMapper;

public interface RolRepository extends JpaRepository<Rol, Integer>{

	@Query(value="SELECT * FROM rol_custom",nativeQuery=true)
	public abstract Collection<RolCustom> findAllCustom();
	
	@Query(value="SELECT * FROM rol_mapper",nativeQuery=true)
	public abstract Collection<RolMapper> findAllMapper();
}
