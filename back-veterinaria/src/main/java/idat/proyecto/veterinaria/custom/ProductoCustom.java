package idat.proyecto.veterinaria.custom;

import java.time.LocalDateTime;

public interface ProductoCustom {

	public Integer getId();
	public String getDescripcion();
	public LocalDateTime getFecha_creacion();
	public String getFoto();
	public String getNombre();
	public String getMarca();
	public Double getPrecio_venta();
	public Double getPrecio_compra();
	public String getUnidad_medida();
	public Integer getStock();
	public Boolean getEliminado();
	public Integer getCategoria_id();
}
