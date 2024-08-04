import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cita, CitaCustom, CitaMapper } from '../Models/cita';
import { HttpClient } from '@angular/common/http';
import { API_URL } from '../Environments/routes';

@Injectable({
  providedIn: 'root'
})
export class CitaService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Cita[]> {
    return this.http.get<Cita[]>(API_URL.citas.find_all);
  }

  findAllCustom(): Observable<CitaCustom[]> {
    return this.http.get<CitaCustom[]>(API_URL.citas.find_all_custom);
  }

  findAllMapper(): Observable<CitaMapper[]> {
    return this.http.get<CitaMapper[]>(API_URL.citas.find_all_mapper);
  }

  findAllByMascotaId(id:number): Observable<CitaMapper[]> {
    return this.http.get<CitaMapper[]>(API_URL.citas.find_all_by_mascota_id(id));
  }

  findAllByEstado(estado:string): Observable<CitaMapper[]> {
    return this.http.get<CitaMapper[]>(API_URL.citas.find_all_by_estado(estado));
  }

  findById(id: number): Observable<Cita> {
    return this.http.get<Cita>(API_URL.citas.find_by_id(id));
  }

  insert(cita: Cita): Observable<any> {
    return this.http.post<any>(API_URL.citas.insert, cita);
  }

  update(id: number, cita: Cita): Observable<any> {
    return this.http.put<any>(API_URL.citas.update(id), cita);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(API_URL.citas.delete(id));
  }

  cancelar(id: number, mensaje:string): Observable<any> {
    return this.http.put<any>(API_URL.citas.cancelar(id), mensaje);
  }

}
