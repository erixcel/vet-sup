package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import idat.proyecto.veterinaria.body.IngresoBody;
import idat.proyecto.veterinaria.entity.Usuario;

@Repository
public interface IngresosRepository extends JpaRepository<Usuario, Integer>{

	@Query(value="call ingresos_del_dia(?)", nativeQuery=true)
	public abstract Collection<IngresoBody> ingresos_del_dia(String fecha);
	
	@Query(value="call suma_total_ingresos(?)", nativeQuery=true)
	public abstract Double suma_total_ingresos(String fecha);
}
