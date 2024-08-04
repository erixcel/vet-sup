package idat.proyecto.veterinaria.mapper;

import java.time.LocalDateTime;

public interface MascotaMapper {
	
	public Integer getId();
	public String getNombre();
	public String getCliente();
	public LocalDateTime getFecha();
	public String getSexo();
	public String getEspecie();
	public String getRaza();
	public String getFoto();
}
