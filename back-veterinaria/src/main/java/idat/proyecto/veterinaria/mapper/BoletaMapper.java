package idat.proyecto.veterinaria.mapper;

import java.time.LocalDateTime;

public interface BoletaMapper {
	
	public Integer getId();
	public String getCliente();
	public String getFoto_cliente();
	public String getUsuario();
	public LocalDateTime getFecha();
	public String getTipo_pago();
	public Double getTotal();
	public String getEstado();
	public String getMensaje();
}
