import { Categoria } from "./categoria";
import { Proveedor } from "./proveedor";

export interface Producto {
  id?: number;
  fecha_creacion?: Date;
  nombre?: string;
  marca?: string;
  descripcion?: string;
  precio_venta?: number;
  precio_compra?: number;
  unidad_medida?: string;
  stock?: number;
  foto?: string;
  eliminado?: boolean;
  categoria?: Categoria;
  proveedores?: Proveedor[];
}

export interface ProductoCustom {
  categoria_id:   number;
  descripcion:    string;
  eliminado:      boolean;
  fecha_creacion: Date;
  foto:           string;
  id:             number;
  nombre:         string;
  marca:          string;
  precio_venta:   number;
  precio_compra:  number;
  unidad_medida:  string;
  stock:          number;
 }

export interface ProductoMapper {
  categoria:      string;
  descripcion:    string;
  fecha:          Date;
  foto:           string;
  id:             number;
  nombre:         string;
  marca:          string;
  precio_venta:   number;
  precio_compra:  number;
  unidad_medida:  string;
  stock:          number;
 }
