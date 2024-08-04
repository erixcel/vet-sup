package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.EspecieCustom;
import idat.proyecto.veterinaria.entity.Especie;
import idat.proyecto.veterinaria.mapper.EspecieMapper;

public interface EspecieRepository extends JpaRepository<Especie, Integer> {
	
	@Query(value="SELECT * FROM especie_custom",nativeQuery=true)
	public abstract Collection<EspecieCustom> findAllCustom();
	
	@Query(value="SELECT * FROM especie_mapper",nativeQuery=true)
	public abstract Collection<EspecieMapper> findAllMapper();
}
