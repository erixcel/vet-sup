package idat.proyecto.veterinaria.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "tratamiento")
public class Tratamiento implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
	@Column(name = "fecha_creacion")
	private LocalDateTime fecha_creacion;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "precio")
	private Double precio;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "eliminado")
	private Boolean eliminado;
	
	@OneToOne
    @JoinColumn(name = "cita_id", unique = true)
    private Cita cita;
	
	@ManyToOne
    @JoinColumn(name = "mascota_id")
	private Mascota mascota;
	
	public Tratamiento() {}
	
	@PrePersist
	public void prePersist() {
		fecha_creacion = LocalDateTime.now();
		eliminado = false;
		if(this.cita != null) this.cita.setFecha_atendida(fecha_creacion);
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

	public Cita getCita() {
		return cita;
	}

	public void setCita(Cita cita) {
		this.cita = cita;
	}

	public Mascota getMascota() {
		return mascota;
	}

	public void setMascota(Mascota mascota) {
		this.mascota = mascota;
	}
	
}
