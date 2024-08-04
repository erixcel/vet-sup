package idat.proyecto.veterinaria.custom;

import java.time.LocalDateTime;

public interface BoletaCustom {
	
	public Integer getId();
	public LocalDateTime getFecha_creacion();
	public String getTipo_pago();
	public Double getVuelto();
	public Double getIgv();
	public Double getPrecio_final();
	public Double getSub_total();
	public String getMensaje();
	public String getEstado();
	public Integer getCliente_id();
	public Integer getUsuario_id();
}
