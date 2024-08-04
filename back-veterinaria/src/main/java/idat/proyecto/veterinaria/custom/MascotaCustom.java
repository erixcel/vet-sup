package idat.proyecto.veterinaria.custom;

import java.time.LocalDateTime;

public interface MascotaCustom {
	
	public Integer getId();
	public Integer getAnios();
	public String getDescripcion();
	public LocalDateTime getFecha_creacion();
	public String getFoto();
	public Integer getMeses();
	public String getNombre();
	public Double getPeso();
	public String getSexo();
	public Boolean getEliminado();
	public Integer getCliente_id();
	public Integer getRaza_id();
}
