import { PedidoProducto } from "./pedido-producto";
import { Proveedor } from "./proveedor";
import { Usuario } from "./usuario";

export interface Pedido {
  detalle_producto?: PedidoProducto[];
  estado?:           string;
  fecha_emision?:    Date;
  fecha_entrega?:    Date;
  id?:               number;
  mensaje?:          string;
  proveedor?:        Proveedor;
  tipo_pago?:        string;
  total?:            number;
  usuario?:          Usuario;
}

export interface PedidoCustom {
  estado:        string;
  fecha_emision: Date;
  fecha_entrega: Date;
  id:            number;
  mensaje:       string;
  proveedor_id:  number;
  tipo_pago:     string;
  total:         number;
  usuario_id:    number;
}

export interface PedidoMapper {
  estado:         string;
  fecha_emision:  Date;
  fecha_entrega:  Date;
  foto_proveedor: string;
  id:             number;
  mensaje:        string;
  proveedor:      string;
  tipo_pago:      string;
  total:          number;
  usuario:        string;
 }
