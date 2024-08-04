import { Injectable } from '@angular/core';
import { API_URL } from '../Environments/routes';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tratamiento, TratamientoCustom, TratamientoMapper } from '../Models/tratamiento';

@Injectable({
  providedIn: 'root'
})
export class TratamientoService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Tratamiento[]> {
    return this.http.get<Tratamiento[]>(API_URL.tratamientos.find_all);
  }

  findAllCustom(): Observable<TratamientoCustom[]> {
    return this.http.get<TratamientoCustom[]>(API_URL.tratamientos.find_all_custom);
  }

  findAllMapper(): Observable<TratamientoMapper[]> {
    return this.http.get<TratamientoMapper[]>(API_URL.tratamientos.find_all_mapper);
  }

  findAllByMascotaId(id:number): Observable<TratamientoMapper[]> {
    return this.http.get<TratamientoMapper[]>(API_URL.tratamientos.find_all_by_mascota_id(id));
  }

  findById(id: number): Observable<Tratamiento> {
    return this.http.get<Tratamiento>(API_URL.tratamientos.find_by_id(id));
  }

  insert(tratamiento: Tratamiento): Observable<Tratamiento> {
    return this.http.post<Tratamiento>(API_URL.tratamientos.insert, tratamiento);
  }

  update(id: number, tratamiento: Tratamiento): Observable<Tratamiento> {
    return this.http.put<Tratamiento>(API_URL.tratamientos.update(id), tratamiento);
  }

  delete(id: number): Observable<string> {
    return this.http.delete(API_URL.tratamientos.delete(id), {responseType: 'text'});
  }

}
