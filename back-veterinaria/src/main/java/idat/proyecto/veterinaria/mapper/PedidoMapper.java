package idat.proyecto.veterinaria.mapper;

import java.time.LocalDateTime;

public interface PedidoMapper {
	
	public Integer getId();
	public LocalDateTime getFecha_emision();
	public LocalDateTime getFecha_entrega();
	public String getTipo_pago();
	public Double getTotal();
	public String getMensaje();
	public String getEstado();
	public String getProveedor();
	public String getFoto_proveedor();
	public String getUsuario();
}
