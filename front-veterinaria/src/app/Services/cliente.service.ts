import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_URL } from '../Environments/routes';
import { Cliente, ClienteCustom, ClienteMapper } from '../Models/cliente';

@Injectable({
  providedIn: 'root'
})

export class ClienteService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(API_URL.clientes.find_all);
  }

  findAllCustom(): Observable<ClienteCustom[]> {
    return this.http.get<ClienteCustom[]>(API_URL.clientes.find_all_custom);
  }

  findAllMapper(): Observable<ClienteMapper[]> {
    return this.http.get<ClienteMapper[]>(API_URL.clientes.find_all_mapper);
  }

  findById(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(API_URL.clientes.find_by_id(id));
  }

  insert(cliente: Cliente): Observable<any> {
    return this.http.post<any>(API_URL.clientes.insert, cliente);
  }

  update(id: number, cliente: Cliente): Observable<any> {
    return this.http.put<any>(API_URL.clientes.update(id), cliente);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(API_URL.clientes.delete(id));
  }

  setFoto(id: number, file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(API_URL.clientes.set_foto(id), formData, {responseType: 'text'});
  }
}
