import { Mascota } from "./mascota";

export interface Cita{
  id?:number,
  fecha_programada?:Date | string,
  fecha_atendida?:Date | string,
  estado?:string,
  motivo?:string,
  hechos?: string,
  eliminado?:boolean,
  mascota?: Mascota
}

export interface CitaCustom {
  eliminado:        boolean;
  estado:           string;
  fecha_atendida:   Date | null;
  fecha_programada: Date;
  hechos:           string;
  id:               number;
  mascota_id:       number;
  motivo:           string;
 }

export interface CitaMapper {
  celular:          number;
  cliente:          string;
  estado:           string;
  fecha_atendida:   Date | null;
  fecha_programada: Date;
  foto_cliente:     string;
  foto_mascota:     string;
  id:               number;
  mascota:          string;
  motivo:           string;
 }
