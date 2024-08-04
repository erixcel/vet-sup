package idat.proyecto.veterinaria.custom;

import java.time.LocalDateTime;

public interface CitaCustom {
	
	public Integer getId();
	public String getEstado();
	public LocalDateTime getFecha_atendida();
	public LocalDateTime getFecha_programada();
	public String getMotivo();
	public String getHechos();
	public Boolean getEliminado();
	public Integer getMascota_id();
}
