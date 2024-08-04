import { Injectable } from '@angular/core';
import { Pedido, PedidoCustom, PedidoMapper } from '../Models/pedido';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { API_URL } from '../Environments/routes';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(API_URL.pedidos.find_all);
  }

  findAllCustom(): Observable<PedidoCustom[]> {
    return this.http.get<PedidoCustom[]>(API_URL.pedidos.find_all_custom);
  }

  findAllMapper(): Observable<PedidoMapper[]> {
    return this.http.get<PedidoMapper[]>(API_URL.pedidos.find_all_mapper);
  }

  findById(id: number): Observable<Pedido> {
    return this.http.get<Pedido>(API_URL.pedidos.find_by_id(id));
  }

  findNewId(): Observable<string> {
    return this.http.get(API_URL.pedidos.find_new_id, {responseType: 'text'});
  }

  insert(pedido: Pedido): Observable<any> {
    return this.http.post<any>(API_URL.pedidos.insert, pedido);
  }

  update(id: number, pedido: Pedido): Observable<any> {
    return this.http.put<any>(API_URL.pedidos.update(id), pedido);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(API_URL.pedidos.delete(id));
  }

  anular(id: number, mensaje:string): Observable<any> {
    return this.http.put<any>(API_URL.pedidos.anular(id), mensaje);
  }

  recibir(id: number): Observable<any> {
    return this.http.put<any>(API_URL.pedidos.recibir(id), null);
  }

  descargar(id: number): Observable<Blob> {
    return this.http.get(API_URL.pedidos.descargar(id), { responseType: 'blob' });
  }

  previsualizar(id: number): Observable<Blob> {
    return this.http.get(API_URL.pedidos.previsualizar(id), { responseType: 'blob' });
  }

}
