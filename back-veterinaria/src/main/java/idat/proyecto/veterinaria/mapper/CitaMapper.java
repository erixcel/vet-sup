package idat.proyecto.veterinaria.mapper;

import java.time.LocalDateTime;

public interface CitaMapper {

	public Integer getId();
	public String getCliente();
	public String getFoto_cliente();
	public Integer getCelular();
	public String getMascota();
	public String getFoto_mascota();
	public String getMotivo();
	public String getEstado();
	public LocalDateTime getFecha_programada();
	public LocalDateTime getFecha_atendida();
}
