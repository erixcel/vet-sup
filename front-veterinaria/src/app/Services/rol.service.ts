import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Rol, RolCustom, RolMapper } from '../Models/rol';
import { API_URL } from '../Environments/routes';

@Injectable({
  providedIn: 'root'
})
export class RolService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Rol[]> {
    return this.http.get<Rol[]>(API_URL.roles.find_all);
  }

  findAllCustom(): Observable<RolCustom[]> {
    return this.http.get<RolCustom[]>(API_URL.roles.find_all_custom);
  }

  findAllMapper(): Observable<RolMapper[]> {
    return this.http.get<RolMapper[]>(API_URL.roles.find_all_mapper);
  }

  findById(id: number): Observable<Rol> {
    return this.http.get<Rol>(API_URL.roles.find_by_id(id));
  }

  insert(rol: Rol): Observable<any> {
    return this.http.post<any>(API_URL.roles.insert, rol);
  }

  update(id: number, rol: Rol): Observable<any> {
    return this.http.put<any>(API_URL.roles.update(id), rol);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(API_URL.roles.delete(id));
  }
}
