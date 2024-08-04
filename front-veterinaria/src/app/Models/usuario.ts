import { Rol } from "./rol";

export interface Usuario {
  id?: number;
  correo?: string;
  eliminado?: boolean;
  password?: string;
  username?: string;
  roles?: Rol[];
 }

export interface UsuarioCustom {
  correo:    string;
  eliminado: boolean;
  id:        number;
  password:  string;
  username:  string;
}

export interface UsuarioMapper {
  correo:   string;
  id:       number;
  username: string;
}
