import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Boleta, BoletaCustom, BoletaMapper } from '../Models/boleta';
import { API_URL } from '../Environments/routes';

@Injectable({
  providedIn: 'root'
})
export class BoletaService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Boleta[]> {
    return this.http.get<Boleta[]>(API_URL.boletas.find_all);
  }

  findAllCustom(): Observable<BoletaCustom[]> {
    return this.http.get<BoletaCustom[]>(API_URL.boletas.find_all_custom);
  }

  findAllMapper(): Observable<BoletaMapper[]> {
    return this.http.get<BoletaMapper[]>(API_URL.boletas.find_all_mapper);
  }

  findById(id: number): Observable<Boleta> {
    return this.http.get<Boleta>(API_URL.boletas.find_by_id(id));
  }

  findNewId(): Observable<string> {
    return this.http.get(API_URL.boletas.find_new_id, {responseType: 'text'});
  }

  insert(boleta: Boleta): Observable<any> {
    return this.http.post<any>(API_URL.boletas.insert, boleta);
  }

  update(id: number, boleta: Boleta): Observable<any> {
    return this.http.put<any>(API_URL.boletas.update(id), boleta);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(API_URL.boletas.delete(id));
  }

  anular(id: number, mensaje:string): Observable<any> {
    return this.http.put<any>(API_URL.boletas.anular(id), mensaje);
  }

  descargar(id: number): Observable<Blob> {
    return this.http.get(API_URL.boletas.descargar(id), { responseType: 'blob' });
  }

  previsualizar(id: number): Observable<Blob> {
    return this.http.get(API_URL.boletas.previsualizar(id), { responseType: 'blob' });
  }
}
