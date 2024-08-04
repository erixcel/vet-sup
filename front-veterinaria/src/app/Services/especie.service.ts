import { Injectable } from '@angular/core';
import { Especie, EspecieCustom, EspecieMapper } from '../Models/especie';
import { Observable } from 'rxjs';
import { API_URL } from '../Environments/routes';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EspecieService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Especie[]> {
    return this.http.get<Especie[]>(API_URL.especies.find_all);
  }

  findAllCustom(): Observable<EspecieCustom[]> {
    return this.http.get<EspecieCustom[]>(API_URL.especies.find_all_custom);
  }

  findAllMapper(): Observable<EspecieMapper[]> {
    return this.http.get<EspecieMapper[]>(API_URL.especies.find_all_mapper);
  }

  findById(id: number): Observable<Especie> {
    return this.http.get<Especie>(API_URL.especies.find_by_id(id));
  }

  insert(especie: Especie): Observable<any> {
    return this.http.post<any>(API_URL.especies.insert, especie);
  }

  update(id: number, especie: Especie): Observable<any> {
    return this.http.put<any>(API_URL.especies.update(id), especie);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(API_URL.especies.delete(id));
  }

}
