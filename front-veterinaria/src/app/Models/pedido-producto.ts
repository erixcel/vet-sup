import { Pedido } from "./pedido";
import { Producto } from "./producto";

export interface PedidoProducto {
  pedido?:        Pedido;
  producto?:      Producto;
  precio_compra?: number;
  cantidad?:      number;
  importe?:       number;
}

export interface PedidoProductoCustom {
  cantidad:      number;
  importe:       number;
  pedido_id:     number;
  precio_compra: number;
  producto_id:   number;
 }

export interface PedidoProductoMapper {
  cantidad:      number;
  importe:       number;
  numero_pedido: number;
  precio_compra: number;
  producto:      string;
}

