import { Injectable } from "@angular/core";
import { CanActivate, Router} from "@angular/router";
import { Observable, of } from "rxjs";
import { catchError, map } from "rxjs/operators";
import { AuthService } from "../Services/auth.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate{
  constructor(
    private router: Router,
    private authService: AuthService
  ){}

  canActivate(): Observable<boolean> {
    return this.authService.verify().pipe(
      map(response => {
        return true;
      }),
      catchError(error => {
        this.router.navigate(['/login']);
        return of(false);
      })
    );
  }

}
