package idat.proyecto.veterinaria.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="producto")
public class Producto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
	@Column(name = "fecha_creacion")
    private LocalDateTime fecha_creacion;
	
	@Column(name = "nombre")
    private String nombre;
	
	@Column(name = "marca")
    private String marca;
	
	@Column(name = "unidad_medida")
    private String unidad_medida;

	@Column(name = "descripcion")
    private String descripcion;
	
	@Column(name = "precio_venta")
    private Double precio_venta;
	
	@Column(name = "precio_compra")
    private Double precio_compra;
	
	@Column(name = "stock")
    private Integer stock;
	
	@Column(name = "foto")
    private String foto;
	
	@Column(name = "eliminado")
    private Boolean eliminado;

	@ManyToOne
	@JoinColumn(name="categoria_id")
	private Categoria categoria;
	
	@JsonIgnore
	@OneToMany(mappedBy="producto")
    private Set<BoletaProducto> boletas;
	
	@JsonIgnore
	@OneToMany(mappedBy="producto")
    private Set<PedidoProducto> pedidos;
	
	@ManyToMany
	@JoinTable(name="producto_proveedor",
			joinColumns=@JoinColumn(name="producto_id"),
			inverseJoinColumns=@JoinColumn(name="proveedor_id") )
	private Set<Proveedor> proveedores = new HashSet<>();
	
	public Producto() {}
	
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
	
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public String getUnidad_medida() {
		return unidad_medida;
	}

	public void setUnidad_medida(String unidad_medida) {
		this.unidad_medida = unidad_medida;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio_venta() {
		return precio_venta;
	}

	public void setPrecio_venta(Double precio_venta) {
		this.precio_venta = precio_venta;
	}

	public Double getPrecio_compra() {
		return precio_compra;
	}

	public void setPrecio_compra(Double precio_compra) {
		this.precio_compra = precio_compra;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Set<BoletaProducto> getBoletas() {
		return boletas;
	}

	public void setBoletas(Set<BoletaProducto> boletas) {
		this.boletas = boletas;
	}
	
	public Set<PedidoProducto> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Set<PedidoProducto> pedidos) {
		this.pedidos = pedidos;
	}

	public Set<Proveedor> getProveedores() {
		return proveedores;
	}

	public void setProveedores(Set<Proveedor> proveedores) {
		this.proveedores = proveedores;
	}
	
}
