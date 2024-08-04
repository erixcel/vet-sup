package idat.proyecto.veterinaria.mapper;

import java.time.LocalDateTime;

public interface ClienteMapper {

	public Integer getId();
	public String getCliente();
	public String getGenero();
	public LocalDateTime getFecha();
	public Integer getCelular();
	public String getFoto();
}
