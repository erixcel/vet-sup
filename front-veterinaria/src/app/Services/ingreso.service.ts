import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from '../Environments/routes';
import { IngresoBody } from '../Models/ingreso';

@Injectable({
  providedIn: 'root'
})
export class IngresoService {

  constructor(private http: HttpClient) { }

  findAllByFecha(fecha: string): Observable<IngresoBody[]> {
    return this.http.get<IngresoBody[]>(API_URL.ingresos.find_all_by_fecha(fecha));
  }

  sumTotalByFecha(fecha: string): Observable<any> {
    return this.http.get<any>(API_URL.ingresos.sum_total_by_fecha(fecha));
  }
}
