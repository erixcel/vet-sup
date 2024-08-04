package idat.proyecto.veterinaria.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="mascota")
public class Mascota implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
	@Column(name = "fecha_creacion")
	private LocalDateTime fecha_creacion;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "sexo")
	private String sexo;
	
	@Column(name = "anios")
	private Integer anios;
	
	@Column(name = "meses")
	private Integer meses;
	
	@Column(name = "peso")
	private Double peso;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "foto")
    private String foto;
	
	@Column(name = "eliminado")
    private Boolean eliminado;
	
	@ManyToOne
    @JoinColumn(name="raza_id")
    private Raza raza;
	
	@ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;
	
	@JsonIgnore
	@OneToMany(mappedBy="mascota")
    private Collection<Banio> banios;
	
	@JsonIgnore
	@OneToMany(mappedBy="mascota")
    private Collection<Tratamiento> tratamientos;
	
	@JsonIgnore
	@OneToMany(mappedBy="mascota")
    private Collection<Cita> citas;
	
	public Mascota() {}
	
	@PrePersist
	public void prePersist() {
		fecha_creacion = LocalDateTime.now();
		foto = "";
		eliminado = false;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Integer getAnios() {
		return anios;
	}

	public void setAnios(Integer anios) {
		this.anios = anios;
	}

	public Integer getMeses() {
		return meses;
	}

	public void setMeses(Integer meses) {
		this.meses = meses;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

	public Raza getRaza() {
		return raza;
	}

	public void setRaza(Raza raza) {
		this.raza = raza;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Collection<Banio> getBanios() {
		return banios;
	}

	public void setBanios(Collection<Banio> banios) {
		this.banios = banios;
	}

	public Collection<Tratamiento> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(Collection<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}

	public Collection<Cita> getCitas() {
		return citas;
	}

	public void setCitas(Collection<Cita> citas) {
		this.citas = citas;
	}
	
}
