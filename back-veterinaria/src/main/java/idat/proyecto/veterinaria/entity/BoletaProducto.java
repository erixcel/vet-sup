package idat.proyecto.veterinaria.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import idat.proyecto.veterinaria.compound.BoletaProductoId;

@Entity
@Table(name="boleta_producto")
public class BoletaProducto{

	@EmbeddedId
	@JsonIgnore
    private BoletaProductoId id = new BoletaProductoId();
	
	@ManyToOne
	@MapsId("boleta_id")
    @JoinColumn(name = "boleta_id")
	@JsonBackReference
    private Boleta boleta;

    @ManyToOne
    @MapsId("producto_id")
    @JoinColumn(name = "producto_id")
    private Producto producto;
    
    @Column(name = "precio")
    private Double precio;
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "total")
    private Double total;
    
    public BoletaProducto() {}
    
    public void calcularTotal() {
        if (this.cantidad != null && this.producto.getPrecio_venta() != null) {
            this.precio = producto.getPrecio_venta();
            this.total = Math.round((this.cantidad * this.precio) * 100.0) / 100.0;
        }
    }

	public BoletaProductoId getId() {
		return id;
	}

	public void setId(BoletaProductoId id) {
		this.id = id;
	}

	public Boleta getBoleta() {
		return boleta;
	}

	public void setBoleta(Boleta boleta) {
		this.boleta = boleta;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
