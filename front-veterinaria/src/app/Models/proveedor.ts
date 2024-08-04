export interface Proveedor {
  celular?:   number;
  correo?:    string;
  eliminado?: boolean;
  id?:        number;
  nombre?:    string;
  foto?:      string;
 }

 export interface ProveedorCustom {
  celular:   string;
  correo:    string;
  eliminado: boolean;
  id:        number;
  nombre:    string;
  foto:      string;
 }

 export interface ProveedorMapper {
  celular: string;
  correo:  string;
  id:      number;
  nombre:  string;
  foto:    string;
 }
