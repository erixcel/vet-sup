export interface Categoria{
  id?:number,
  nombre?:string,
  eliminado?:boolean
}

export interface CategoriaCustom {
  eliminado: boolean;
  id:        number;
  nombre:    string;
 }

export interface CategoriaMapper {
  id:     number;
  nombre: string;
 }
