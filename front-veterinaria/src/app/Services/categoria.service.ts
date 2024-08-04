import { Injectable } from '@angular/core';
import { Categoria, CategoriaCustom, CategoriaMapper } from '../Models/categoria';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { API_URL } from '../Environments/routes';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(API_URL.categorias.find_all);
  }

  findAllCustom(): Observable<CategoriaCustom[]> {
    return this.http.get<CategoriaCustom[]>(API_URL.categorias.find_all_custom);
  }

  findAllMapper(): Observable<CategoriaMapper[]> {
    return this.http.get<CategoriaMapper[]>(API_URL.categorias.find_all_mapper);
  }

  findById(id: number): Observable<Categoria> {
    return this.http.get<Categoria>(API_URL.categorias.find_by_id(id));
  }

  insert(categoria: Categoria): Observable<any> {
    return this.http.post<any>(API_URL.categorias.insert, categoria);
  }

  update(id: number, categoria: Categoria): Observable<any> {
    return this.http.put<any>(API_URL.categorias.update(id), categoria);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(API_URL.categorias.delete(id));
  }
}
