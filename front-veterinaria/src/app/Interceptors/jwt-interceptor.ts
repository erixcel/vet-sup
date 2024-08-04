import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const jwt_token = localStorage.getItem('jwt-token-veterinaria');
    if (jwt_token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${jwt_token}`
        }
      })
    }
    return next.handle(request);
  }
}
