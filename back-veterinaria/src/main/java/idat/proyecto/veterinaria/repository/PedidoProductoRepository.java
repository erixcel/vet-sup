package idat.proyecto.veterinaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import idat.proyecto.veterinaria.compound.PedidoProductoId;
import idat.proyecto.veterinaria.custom.PedidoProductoCustom;
import idat.proyecto.veterinaria.entity.PedidoProducto;
import idat.proyecto.veterinaria.mapper.PedidoProductoMapper;

public interface PedidoProductoRepository extends JpaRepository<PedidoProducto, PedidoProductoId>{

	@Query(value="SELECT * FROM pedido_producto_custom",nativeQuery=true)
	public abstract Collection<PedidoProductoCustom> findAllCustom();
	
	@Query(value="SELECT * FROM pedido_producto_mapper",nativeQuery=true)
	public abstract Collection<PedidoProductoMapper> findAllMapper();
}
