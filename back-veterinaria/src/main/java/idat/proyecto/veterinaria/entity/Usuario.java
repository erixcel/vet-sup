package idat.proyecto.veterinaria.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="usuario")
public class Usuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
	@Column(name = "username", unique = true)
    private String username;
	
	@Column(name = "correo", unique = true)
    private String correo;
	
	@Column(name = "password")
    private String password;
	
	@Column(name = "eliminado")
	private Boolean eliminado;
	
	@JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private Collection<Boleta> boletas;
	
	@ManyToMany
	@JoinTable(name="usuario_rol",
			joinColumns=@JoinColumn(name="usuario_id"),
			inverseJoinColumns=@JoinColumn(name="rol_id") )
	private Set<Rol> roles = new HashSet<>();
	
	@PrePersist
	public void prePersist() {
		eliminado = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public String getPassword() {
		return password;
	}

	public Boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Collection<Boleta> getBoletas() {
		return boletas;
	}

	public void setBoletas(Collection<Boleta> boletas) {
		this.boletas = boletas;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	
}
