import { Boleta } from "./boleta";
import { Producto } from "./producto";

export interface BoletaProducto {
  boleta?: Boleta;
  producto?: Producto;
  precio?: number;
  cantidad?: number;
  total?: number;
}

export interface BoletaProductoCustom {
  boleta_id: number;
  producto_id: number;
  precio: number;
  cantidad: number;
  total: number;
}

export interface BoletaProductoMapper {
  numero_boleta: number;
  producto: string;
  precio: number;
  cantidad: number;
  total: number;
}


