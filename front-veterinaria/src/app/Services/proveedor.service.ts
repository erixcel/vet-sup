import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Proveedor } from '../Models/proveedor';
import { ProveedorCustom, ProveedorMapper } from '../Models/proveedor';
import { API_URL } from '../Environments/routes';
import { Producto } from '../Models/producto';

@Injectable({
  providedIn: 'root'
})
export class ProveedorService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Proveedor[]> {
    return this.http.get<Proveedor[]>(API_URL.proveedores.find_all);
  }

  findAllCustom(): Observable<ProveedorCustom[]> {
    return this.http.get<ProveedorCustom[]>(API_URL.proveedores.find_all_custom);
  }

  findAllMapper(): Observable<ProveedorMapper[]> {
    return this.http.get<ProveedorMapper[]>(API_URL.proveedores.find_all_mapper);
  }

  findById(id: number): Observable<Proveedor> {
    return this.http.get<Proveedor>(API_URL.proveedores.find_by_id(id));
  }

  findProductosById(id: number): Observable<Producto[]> {
    return this.http.get<Producto[]>(API_URL.proveedores.find_productos_by_id(id));
  }

  insert(proveedor: Proveedor): Observable<any> {
    return this.http.post<any>(API_URL.proveedores.insert, proveedor);
  }

  update(id: number, proveedor: Proveedor): Observable<any> {
    return this.http.put<any>(API_URL.proveedores.update(id), proveedor);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(API_URL.proveedores.delete(id));
  }

  setFoto(id: number, file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(API_URL.proveedores.set_foto(id), formData, {responseType: 'text'});
  }
}
