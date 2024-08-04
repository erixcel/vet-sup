package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.UsuarioCustom;
import idat.proyecto.veterinaria.entity.Usuario;
import idat.proyecto.veterinaria.mapper.UsuarioMapper;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	public abstract Usuario findByUsername(String username);
	
	public abstract Usuario findByCorreo(String correo);
	
	@Query(value="SELECT * FROM usuario_custom",nativeQuery=true)
	public abstract Collection<UsuarioCustom> findAllCustom();
	
	@Query(value="SELECT * FROM usuario_mapper",nativeQuery=true)
	public abstract Collection<UsuarioMapper> findAllMapper();
}
