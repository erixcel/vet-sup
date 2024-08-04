export interface Cliente {
  id?: number;
  fecha_creacion?: Date;
  nombre?: string;
  apellidos?: string;
  genero?: string;
  celular?: number;
  direccion?: string;
  foto?: string;
  eliminado?: boolean;
}

export interface ClienteCustom {
  apellidos:      string;
  celular:        number;
  direccion:      string;
  fecha_creacion: Date;
  foto:           string;
  genero:         string;
  id:             number;
  nombre:         string;
 }

export interface ClienteMapper {
  celular: number;
  cliente: string;
  fecha:   Date;
  foto:    string;
  genero:  string;
  id:      number;
}
