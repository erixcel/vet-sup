import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Mascota, MascotaCustom, MascotaMapper } from '../Models/mascota';
import { API_URL } from '../Environments/routes';

@Injectable({
  providedIn: 'root'
})
export class MascotaService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(API_URL.mascotas.find_all);
  }

  findAllCustom(): Observable<MascotaCustom[]> {
    return this.http.get<MascotaCustom[]>(API_URL.mascotas.find_all_custom);
  }

  findAllMapper(): Observable<MascotaMapper[]> {
    return this.http.get<MascotaMapper[]>(API_URL.mascotas.find_all_mapper);
  }

  findAllByClienteId(id: number): Observable<MascotaMapper[]> {
    return this.http.get<MascotaMapper[]>(API_URL.mascotas.find_all_by_cliente_id(id));
  }

  findById(id: number): Observable<Mascota> {
    return this.http.get<Mascota>(API_URL.mascotas.find_by_id(id));
  }

  insert(mascota: Mascota): Observable<any> {
    return this.http.post<any>(API_URL.mascotas.insert, mascota);
  }

  update(id: number, mascota: Mascota): Observable<any> {
    return this.http.put<any>(API_URL.mascotas.update(id), mascota);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(API_URL.mascotas.delete(id));
  }

  setFoto(id: number, file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(API_URL.mascotas.set_foto(id), formData, {responseType: 'text'});
  }
}
