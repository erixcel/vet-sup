package idat.proyecto.veterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import idat.proyecto.veterinaria.entity.Usuario;

@Repository
public interface PanelRepository extends JpaRepository<Usuario, Integer>{

	@Query(value="select count(*) from tratamiento where eliminado = 0", nativeQuery=true)
	public abstract Integer totalTratamientos();
	
	@Query(value="select count(*) from cliente where eliminado = 0", nativeQuery=true)
	public abstract Integer totalClientes();
	
	@Query(value="select count(*) from mascota where eliminado = 0", nativeQuery=true)
	public abstract Integer totalMascotas();
	
	@Query(value="select count(*) from banio where eliminado = 0", nativeQuery=true)
	public abstract Integer totalBanios();
	
	@Query(value="select count(*) from cita where eliminado = 0 and estado = 'atendida'", nativeQuery=true)
	public abstract Integer totalCitasAtendidas();
	
	@Query(value="select count(*) from boleta", nativeQuery=true)
	public abstract Integer totalBoletasFacturadas();
}
