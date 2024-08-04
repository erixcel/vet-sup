import { Especie } from "./especie";

export interface Raza {
  id?: number,
  nombre?: string,
  eliminado?: boolean,
  especie?: Especie
}

export interface RazaCustom {
  especie_id: number;
  id:         number;
  nombre:     string;
 }

export interface RazaMapper {
  especie: string;
  id:      number;
  nombre:  string;
 }
