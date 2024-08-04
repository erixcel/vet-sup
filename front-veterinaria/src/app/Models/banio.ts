import { Cita } from "./cita";
import { Mascota } from "./mascota";

export interface Banio {
  id?: number,
  fecha_creacion?: Date,
  tipo?: string,
  precio?: number,
  detalles?: string,
  foto_entrada?: string,
  foto_salida?: string,
  eliminado?: boolean,
  mascota?: Mascota,
  cita?: Cita,
}

export interface BanioCustom {
  cita_id:        number | null;
  detalles:       string;
  eliminado:      boolean;
  fecha_creacion: Date;
  foto_entrada:   string;
  foto_salida:    string;
  id:             number;
  mascota_id:     number;
  precio:         number;
  tipo:           string;
 }

export interface BanioMapper {
  celular:      number;
  citado:       string;
  cliente:      string;
  fecha:        Date;
  foto_cliente: string;
  foto_mascota: string;
  id:           number;
  mascota:      string;
  precio:       number;
  tipo:         string;
}
