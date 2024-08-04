package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.ProductoCustom;
import idat.proyecto.veterinaria.entity.Producto;
import idat.proyecto.veterinaria.mapper.ProductoMapper;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{

	@Query(value="SELECT * FROM producto_custom",nativeQuery=true)
	public abstract Collection<ProductoCustom> findAllCustom();
	
	@Query(value="SELECT * FROM producto_mapper",nativeQuery=true)
	public abstract Collection<ProductoMapper> findAllMapper();
}
