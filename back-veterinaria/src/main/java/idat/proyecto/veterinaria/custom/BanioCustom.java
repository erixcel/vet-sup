package idat.proyecto.veterinaria.custom;

import java.time.LocalDateTime;

public interface BanioCustom {
	
	public Integer getId();
	public String getDetalles();
	public LocalDateTime getFecha_creacion();
	public String getFoto_entrada();
	public String getFoto_salida();
	public Double getPrecio();
	public String getTipo();
	public Boolean getEliminado();
	public Integer getCita_id();
	public Integer getMascota_id();
}
