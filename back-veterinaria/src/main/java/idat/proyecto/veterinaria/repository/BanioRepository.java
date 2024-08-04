package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.BanioCustom;
import idat.proyecto.veterinaria.entity.Banio;
import idat.proyecto.veterinaria.mapper.BanioMapper;

public interface BanioRepository extends JpaRepository<Banio, Integer> {

	@Query(value="SELECT * FROM banio_custom",nativeQuery=true)
	public abstract Collection<BanioCustom> findAllCustom();

	@Query(value="SELECT * FROM banio_mapper",nativeQuery=true)
	public abstract Collection<BanioMapper> findAllMapper();
	
	@Query(value="SELECT * FROM get_banios_by_mascota_id(?)",nativeQuery=true)
	public abstract Collection<BanioMapper> findAllByMascotaId(Integer mascota_id);
}
