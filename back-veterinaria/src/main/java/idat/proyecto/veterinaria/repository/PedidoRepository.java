package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.custom.PedidoCustom;
import idat.proyecto.veterinaria.entity.Pedido;
import idat.proyecto.veterinaria.mapper.PedidoMapper;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	@Query(value="SELECT * FROM pedido_custom",nativeQuery=true)
	public abstract Collection<PedidoCustom> findAllCustom();
	
	@Query(value="SELECT * FROM pedido_mapper",nativeQuery=true)
	public abstract Collection<PedidoMapper> findAllMapper();
	
	@Query(value="SELECT IFNULL(MAX(id) + 1, 1) FROM pedido",nativeQuery=true)
	public abstract Integer findNewId();
}
