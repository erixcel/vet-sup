import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from '../Environments/routes';

@Injectable({
  providedIn: 'root'
})
export class PanelService {

  constructor(private http: HttpClient) { }

  total_tratamientos(): Observable<any> {
    return this.http.get(API_URL.panel.total_tratamientos);
  }
  total_clientes(): Observable<any> {
    return this.http.get(API_URL.panel.total_clientes);
  }
  total_mascotas(): Observable<any> {
    return this.http.get(API_URL.panel.total_mascotas);
  }
  total_banios(): Observable<any> {
    return this.http.get(API_URL.panel.total_banios);
  }
  total_citas(): Observable<any> {
    return this.http.get(API_URL.panel.total_citas);
  }
  total_boletas(): Observable<any> {
    return this.http.get(API_URL.panel.total_boletas);
  }
}
