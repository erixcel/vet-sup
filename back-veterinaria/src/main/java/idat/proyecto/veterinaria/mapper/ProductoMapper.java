package idat.proyecto.veterinaria.mapper;

import java.time.LocalDateTime;

public interface ProductoMapper {

	public Integer getId();
	public String getNombre();
	public String getCategoria();
	public String getMarca();
	public String getDescripcion();
	public LocalDateTime getFecha();
	public Integer getStock();
	public Double getPrecio_venta();
	public Double getPrecio_compra();
	public String getUnidad_medida();
	public String getFoto();
}
