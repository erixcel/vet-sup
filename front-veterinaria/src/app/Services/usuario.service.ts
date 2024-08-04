import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario, UsuarioCustom, UsuarioMapper } from '../Models/usuario';
import { API_URL } from '../Environments/routes';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(API_URL.usuarios.find_all);
  }

  findAllCustom(): Observable<UsuarioCustom[]> {
    return this.http.get<UsuarioCustom[]>(API_URL.usuarios.find_all_custom);
  }

  findAllMapper(): Observable<UsuarioMapper[]> {
    return this.http.get<UsuarioMapper[]>(API_URL.usuarios.find_all_mapper);
  }

  findById(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(API_URL.usuarios.find_by_id(id));
  }

  insert(usuario: Usuario): Observable<any> {
    return this.http.post<any>(API_URL.usuarios.insert, usuario);
  }

  update(id: number, usuario: Usuario): Observable<any> {
    return this.http.put<any>(API_URL.usuarios.update(id), usuario);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(API_URL.usuarios.delete(id));
  }
}
