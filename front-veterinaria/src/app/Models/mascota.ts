import { Cliente } from "./cliente";
import { Raza } from "./raza";

export interface Mascota {
  id?: number;
  fecha_creacion?: Date;
  nombre?: string;
  sexo?: string;
  anios?: number;
  meses?: number;
  peso?: number;
  descripcion?: string;
  foto?: string;
  eliminado?: boolean;
  raza?: Raza;
  cliente?:Cliente;
}

export interface MascotaCustom {
  anios:          number;
  cliente_id:     number;
  descripcion:    string;
  fecha_creacion: Date;
  foto:           string;
  id:             number;
  meses:          number;
  nombre:         string;
  peso:           number;
  raza_id:        number;
  sexo:           string;
 }

 export interface MascotaMapper {
  cliente: string;
  especie: string;
  fecha:   Date;
  foto:    string;
  id:      number;
  nombre:  string;
  raza:    string;
  sexo:    string;
 }
