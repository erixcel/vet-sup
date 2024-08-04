import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from '../Environments/routes';
import { HttpClient } from '@angular/common/http';
import { Usuario } from '../Models/usuario';

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  constructor(private http: HttpClient) { }

  login(usuario: Usuario): Observable<any> {
    return this.http.post(API_URL.auth.login, usuario);
  }

  verify(): Observable<any> {
    return this.http.get(API_URL.auth.verify);
  }

  usuario(): Observable<Usuario> {
    return this.http.get(API_URL.auth.usuario);
  }
}
