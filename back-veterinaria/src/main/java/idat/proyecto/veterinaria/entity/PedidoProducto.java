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

import idat.proyecto.veterinaria.compound.PedidoProductoId;

@Entity
@Table(name="pedido_producto")
public class PedidoProducto {

	@EmbeddedId
	@JsonIgnore
    private PedidoProductoId id = new PedidoProductoId();
	
	@ManyToOne
	@MapsId("pedido_id")
    @JoinColumn(name = "pedido_id")
	@JsonBackReference
    private Pedido pedido;

    @ManyToOne
    @MapsId("producto_id")
    @JoinColumn(name = "producto_id")
    private Producto producto;
    
    @Column(name = "precio_compra")
    private Double precio_compra;
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "importe")
    private Double importe;
    
    public PedidoProducto() {}
    
    public void calcularImporte() {
        if (this.cantidad != null && this.producto.getPrecio_compra() != null) {
            this.precio_compra = producto.getPrecio_compra();
            this.importe = Math.round((this.cantidad * this.precio_compra) * 100.0) / 100.0;
        }
    }

	public PedidoProductoId getId() {
		return id;
	}

	public void setId(PedidoProductoId id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Double getPrecio_compra() {
		return precio_compra;
	}

	public void setPrecio_compra(Double precio_compra) {
		this.precio_compra = precio_compra;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}
	
}
