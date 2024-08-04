package idat.proyecto.veterinaria.custom;

import java.time.LocalDateTime;

public interface ClienteCustom {

	public Integer getId();
	public String getApellidos();
	public Integer getCelular();
	public String getDireccion();
	public LocalDateTime getFecha_creacion();
	public String getFoto();
	public String getGenero();
	public String getNombre();
	public Boolean getEliminado();
}
