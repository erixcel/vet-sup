import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Raza, RazaCustom, RazaMapper } from '../Models/raza';
import { API_URL } from '../Environments/routes';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RazaService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Raza[]> {
    return this.http.get<Raza[]>(API_URL.razas.find_all);
  }

  findAllCustom(): Observable<RazaCustom[]> {
    return this.http.get<RazaCustom[]>(API_URL.razas.find_all_custom);
  }

  findAllMapper(): Observable<RazaMapper[]> {
    return this.http.get<RazaMapper[]>(API_URL.razas.find_all_mapper);
  }

  findById(id: number): Observable<Raza> {
    return this.http.get<Raza>(API_URL.razas.find_by_id(id));
  }

  insert(raza: Raza): Observable<any> {
    return this.http.post<any>(API_URL.razas.insert, raza);
  }

  update(id: number, raza: Raza): Observable<any> {
    return this.http.put<any>(API_URL.razas.update(id), raza);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(API_URL.razas.delete(id));
  }
}
