package idat.proyecto.veterinaria.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;


@Entity
@Table(name="boleta")
public class Boleta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
	@Column(name = "fecha_creacion")
    private LocalDateTime fecha_creacion;
	
	@Column(name = "tipo_pago")
    private String tipo_pago;
	
	@Column(name = "vuelto")
    private Double vuelto;
	
	@Column(name = "sub_total")
    private Double sub_total;
	
	@Column(name = "igv")
    private Double igv;
	
	@Column(name = "precio_final")
    private Double precio_final;
	
	@Column(name = "estado", columnDefinition = "ENUM('emitida', 'anulada')")
	private String estado;
	
	@Column(name = "mensaje")
	private String mensaje;

	@ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;
	
	@ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
	
	@OneToMany(mappedBy="boleta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BoletaProducto> detalle_producto = new HashSet<>();
	
	public Boleta(){}
	
	@PrePersist
	public void prePersist() {
		fecha_creacion = LocalDateTime.now();
		estado = "emitida";
		mensaje = null;
		if(!tipo_pago.equals("efectivo")) vuelto = 0.0;
		if(vuelto == null) vuelto = 0.0;
	}
	
	public void calcularPrecioFinal() {
		this.sub_total = 0.0;
		if(this.detalle_producto.isEmpty()) {
			this.igv = 0.0;
			this.precio_final = 0.0;
		} else {
			this.sub_total = this.detalle_producto.stream().map(BoletaProducto::getTotal).reduce(0.0, Double::sum);
			this.sub_total = Math.round(this.sub_total * 100.0) / 100.0;
			this.igv = Math.round((this.sub_total * 0.18) * 100.0) / 100.0;
			this.precio_final = Math.round((this.sub_total + this.igv) * 100.0) / 100.0;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(LocalDateTime fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
	
	public String getTipo_pago() {
		return tipo_pago;
	}

	public void setTipo_pago(String tipo_pago) {
		this.tipo_pago = tipo_pago;
	}

	public Double getVuelto() {
		return vuelto;
	}

	public void setVuelto(Double vuelto) {
		this.vuelto = vuelto;
	}

	public Double getSub_total() {
		return sub_total;
	}

	public void setSub_total(Double sub_total) {
		this.sub_total = sub_total;
	}

	public Double getIgv() {
		return igv;
	}

	public void setIgv(Double igv) {
		this.igv = igv;
	}

	public Double getPrecio_final() {
		return precio_final;
	}

	public void setPrecio_final(Double precio_final) {
		this.precio_final = precio_final;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Set<BoletaProducto> getDetalle_producto() {
		return detalle_producto;
	}

	public void setDetalle_producto(Set<BoletaProducto> detalle_producto) {
		this.detalle_producto = detalle_producto;
	}

}
