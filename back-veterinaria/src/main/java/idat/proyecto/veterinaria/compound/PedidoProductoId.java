package idat.proyecto.veterinaria.compound;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PedidoProductoId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "pedido_id")
    private Integer pedido_id;

	@Column(name = "producto_id")
    private Integer producto_id;
	
	public PedidoProductoId() {}

	public PedidoProductoId(Integer pedido_id, Integer producto_id) {
		super();
		this.pedido_id = pedido_id;
		this.producto_id = producto_id;
	}

	public Integer getPedido_id() {
		return pedido_id;
	}

	public void setPedido_id(Integer pedido_id) {
		this.pedido_id = pedido_id;
	}

	public Integer getProducto_id() {
		return producto_id;
	}

	public void setProducto_id(Integer producto_id) {
		this.producto_id = producto_id;
	}
}
