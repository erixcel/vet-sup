package idat.proyecto.veterinaria.custom;

import java.time.LocalDateTime;

public interface TratamientoCustom {

	public Integer getId();
	public String getDescripcion();
	public LocalDateTime getFecha_creacion();
	public Double getPrecio();
	public String getTipo();
	public Boolean getEliminado();
	public Integer getCita_id();
	public Integer getMascota_id();
}
