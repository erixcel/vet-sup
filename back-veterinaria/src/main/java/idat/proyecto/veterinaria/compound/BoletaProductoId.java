package idat.proyecto.veterinaria.compound;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BoletaProductoId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "boleta_id")
    private Integer boleta_id;

	@Column(name = "producto_id")
    private Integer producto_id;
    
    public BoletaProductoId() {}   

	public BoletaProductoId(Integer boleta_id, Integer producto_id) {
		this.boleta_id = boleta_id;
		this.producto_id = producto_id;
	}

	public Integer getBoleta_id() {
		return boleta_id;
	}

	public void setBoleta_id(Integer boleta_id) {
		this.boleta_id = boleta_id;
	}

	public Integer getProducto_id() {
		return producto_id;
	}

	public void setProducto_id(Integer producto_id) {
		this.producto_id = producto_id;
	}
}
