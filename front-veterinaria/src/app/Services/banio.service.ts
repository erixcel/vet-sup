import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Banio, BanioCustom, BanioMapper } from '../Models/banio';
import { HttpClient } from '@angular/common/http';
import { API_URL } from '../Environments/routes';

@Injectable({
  providedIn: 'root'
})
export class BanioService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Banio[]> {
    return this.http.get<Banio[]>(API_URL.banios.find_all);
  }

  findAllCustom(): Observable<BanioCustom[]> {
    return this.http.get<BanioCustom[]>(API_URL.banios.find_all_custom);
  }

  findAllMapper(): Observable<BanioMapper[]> {
    return this.http.get<BanioMapper[]>(API_URL.banios.find_all_mapper);
  }

  findAllByMascotaId(id:number): Observable<BanioMapper[]> {
    return this.http.get<BanioMapper[]>(API_URL.banios.find_all_by_mascota_id(id));
  }

  findById(id: number): Observable<Banio> {
    return this.http.get<Banio>(API_URL.banios.find_by_id(id));
  }

  insert(banio: Banio): Observable<Banio> {
    return this.http.post<Banio>(API_URL.banios.insert, banio);
  }

  update(id: number, banio: Banio): Observable<Banio> {
    return this.http.put<Banio>(API_URL.banios.update(id), banio);
  }

  delete(id: number): Observable<string> {
    return this.http.delete(API_URL.banios.delete(id), {responseType: 'text'});
  }
}
