import { Cliente } from "./cliente"
import { BoletaProducto } from "./boleta-producto"

export interface Boleta{
  id?:number,
  fecha_creacion?:Date,
  tipo_pago?:string,
  vuelto?:number,
  sub_total?:number,
  igv?:number,
  precio_final?:number,
  estado?:string,
  cliente?: Cliente,
  detalle_producto?: BoletaProducto[]
}

export interface BoletaCustom {
  cliente_id:     number;
  estado:         string;
  fecha_creacion: Date;
  id:             number;
  igv:            number;
  precio_final:   number;
  sub_total:      number;
  tipo_pago:      string;
  mensaje:        string;
  usuario_id:     number;
  vuelto:         number;
 }

 export interface BoletaMapper {
  cliente:      string;
  fecha:        Date;
  foto_cliente: string;
  id:           number;
  total:        number;
  usuario:      string;
  tipo_pago:    string;
  estado:       string;
  mensaje:      string;
 }
