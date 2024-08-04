package idat.proyecto.veterinaria.mapper;

import java.time.LocalDateTime;

public interface BanioMapper {
	
	public Integer getId();
	public String getCliente();
	public String getFoto_cliente();
	public Integer getCelular();
	public String getMascota();
	public String getFoto_mascota();
	public String getTipo();
	public LocalDateTime getFecha();
	public String getCitado();
	public Double getPrecio();
}
