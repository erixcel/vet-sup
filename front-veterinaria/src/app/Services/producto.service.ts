import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, of } from 'rxjs';
import { Producto, ProductoCustom, ProductoMapper } from '../Models/producto';
import { API_URL } from '../Environments/routes';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Producto[]> {
    return this.http.get<Producto[]>(API_URL.productos.find_all);
  }

  findAllCustom(): Observable<ProductoCustom[]> {
    return this.http.get<ProductoCustom[]>(API_URL.productos.find_all_custom);
  }

  findAllMapper(): Observable<ProductoMapper[]> {
    return this.http.get<ProductoMapper[]>(API_URL.productos.find_all_mapper);
  }

  findById(id: number): Observable<Producto> {
    return this.http.get<Producto>(API_URL.productos.find_by_id(id));
  }

  insert(producto: Producto): Observable<any> {
    return this.http.post<any>(API_URL.productos.insert, producto);
  }

  update(id: number, producto: Producto): Observable<any> {
    return this.http.put<any>(API_URL.productos.update(id), producto);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(API_URL.productos.delete(id));
  }

  setFoto(id: number, file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(API_URL.productos.set_foto(id), formData, {responseType: 'text'});
  }
}
