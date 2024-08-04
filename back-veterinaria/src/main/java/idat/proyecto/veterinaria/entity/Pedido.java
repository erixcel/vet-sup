package idat.proyecto.veterinaria.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import idat.proyecto.veterinaria.enumerated.PedidoEstado;

@Entity
@Table(name="pedido")
public class Pedido implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
	@Column(name = "fecha_emision")
    private LocalDateTime fecha_emision;
	
	@Column(name = "fecha_entrega")
    private LocalDateTime fecha_entrega;
	
	@Column(name = "tipo_pago")
    private String tipo_pago;
	
	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private PedidoEstado estado;
	
	@Column(name = "mensaje")
	private String mensaje;
	
	@Column(name = "total")
    private Double total;
	
	@ManyToOne
    @JoinColumn(name="proveedor_id")
    private Proveedor proveedor;
	
	@ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
	
	@OneToMany(mappedBy="pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PedidoProducto> detalle_producto = new HashSet<>();
	
	public Pedido() {}
	
	@PrePersist
	public void prePersist() {
		fecha_emision = LocalDateTime.now();
		estado = PedidoEstado.enviada;
		mensaje = null;
		fecha_entrega = null;
	}
	
	public void calcularTotal() {
		if(this.detalle_producto.isEmpty()) {
			this.total = 0.0;
		} else {
			this.total = this.detalle_producto.stream().map(PedidoProducto::getImporte).reduce(0.0, Double::sum);
			this.total = Math.round(this.total * 100.0) / 100.0;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getFecha_emision() {
		return fecha_emision;
	}

	public void setFecha_emision(LocalDateTime fecha_emision) {
		this.fecha_emision = fecha_emision;
	}

	public LocalDateTime getFecha_entrega() {
		return fecha_entrega;
	}

	public void setFecha_entrega(LocalDateTime fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}

	public String getTipo_pago() {
		return tipo_pago;
	}

	public void setTipo_pago(String tipo_pago) {
		this.tipo_pago = tipo_pago;
	}

	public PedidoEstado getEstado() {
		return estado;
	}

	public void setEstado(PedidoEstado estado) {
		this.estado = estado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Set<PedidoProducto> getDetalle_producto() {
		return detalle_producto;
	}

	public void setDetalle_producto(Set<PedidoProducto> detalle_producto) {
		this.detalle_producto = detalle_producto;
	}

}
