import { Cita } from "./cita";
import { Mascota } from "./mascota";

export interface Tratamiento{
  id?: number,
  fecha_creacion?: Date,
  tipo?: string,
  precio?: number,
  descripcion?: string,
  eliminado?: boolean,
  mascota?: Mascota,
  cita?: Cita,
}

export interface TratamientoCustom {
  cita_id:        number | null;
  descripcion:    string;
  fecha_creacion: Date;
  id:             number;
  mascota_id:     number;
  precio:         number;
  tipo:           string;
 }

export interface TratamientoMapper {
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
