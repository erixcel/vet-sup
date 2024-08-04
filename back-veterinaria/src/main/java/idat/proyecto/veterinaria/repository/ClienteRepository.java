package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.ClienteCustom;
import idat.proyecto.veterinaria.entity.Cliente;
import idat.proyecto.veterinaria.mapper.ClienteMapper;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	@Query(value="SELECT * FROM cliente_custom",nativeQuery=true)
	public abstract Collection<ClienteCustom> findAllCustom();
	
	@Query(value="SELECT * FROM cliente_mapper",nativeQuery=true)
	public abstract Collection<ClienteMapper> findAllMapper();
}
