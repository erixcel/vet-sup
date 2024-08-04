package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.CategoriaCustom;
import idat.proyecto.veterinaria.entity.Categoria;
import idat.proyecto.veterinaria.mapper.CategoriaMapper;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

	@Query(value="SELECT * FROM categoria_custom",nativeQuery=true)
	public abstract Collection<CategoriaCustom> findAllCustom();
	
	@Query(value="SELECT * FROM categoria_mapper",nativeQuery=true)
	public abstract Collection<CategoriaMapper> findAllMapper();
}
