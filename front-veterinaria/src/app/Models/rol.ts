export interface Rol {
  id?: number;
  eliminado?: boolean;
  nombre?: string;
}

export interface RolCustom {
  eliminado: boolean;
  id:        number;
  nombre:    string;
}

export interface RolMapper {
  id:     number;
  nombre: string;
 }

