package idat.proyecto.veterinaria.custom;

import java.time.LocalDateTime;

public interface PedidoCustom {
	
	public Integer getId();
	public LocalDateTime getFecha_emision();
	public LocalDateTime getFecha_entrega();
	public String getTipo_pago();
	public Double getTotal();
	public String getMensaje();
	public String getEstado();
	public Integer getProveedor_id();
	public Integer getUsuario_id();
}
