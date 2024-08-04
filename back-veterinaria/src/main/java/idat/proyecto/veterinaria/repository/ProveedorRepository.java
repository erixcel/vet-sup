package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.ProveedorCustom;
import idat.proyecto.veterinaria.entity.Proveedor;
import idat.proyecto.veterinaria.mapper.ProveedorMapper;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>{

	@Query(value="SELECT * FROM proveedor_custom",nativeQuery=true)
	public abstract Collection<ProveedorCustom> findAllCustom();
	
	@Query(value="SELECT * FROM proveedor_mapper",nativeQuery=true)
	public abstract Collection<ProveedorMapper> findAllMapper();
}
