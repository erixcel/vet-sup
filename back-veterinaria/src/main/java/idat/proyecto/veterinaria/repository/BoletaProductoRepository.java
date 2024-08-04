package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.compound.BoletaProductoId;
import idat.proyecto.veterinaria.custom.BoletaProductoCustom;
import idat.proyecto.veterinaria.entity.BoletaProducto;
import idat.proyecto.veterinaria.mapper.BoletaProductoMapper;

public interface BoletaProductoRepository extends JpaRepository<BoletaProducto, BoletaProductoId> {
	
	@Query(value="SELECT * FROM boleta_producto_custom",nativeQuery=true)
	public abstract Collection<BoletaProductoCustom> findAllCustom();
	
	@Query(value="SELECT * FROM boleta_producto_mapper",nativeQuery=true)
	public abstract Collection<BoletaProductoMapper> findAllMapper();
	
}
