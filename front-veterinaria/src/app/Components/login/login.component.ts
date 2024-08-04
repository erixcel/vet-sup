import { Usuario } from 'src/app/Models/usuario'
import { AuthService } from '../../Services/auth.service'
import { Component, OnInit } from '@angular/core'
import { Router } from '@angular/router'
import { FlashMessagesComponent } from 'flash-messages-angular'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  usuario: Usuario = {}
  maxAttempts = 5
  lockoutTime = 10 * 60 * 1000 // 10 minutes in milliseconds

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {}

  login(flash: FlashMessagesComponent) {
    const attempts = Number(localStorage.getItem("loginAttempts") || 0)
    const lockedUntil = Number(localStorage.getItem("lockedUntil") || 0)
    const now = Date.now()

    if (attempts >= this.maxAttempts && now < lockedUntil) {
      const timeLeft = Math.ceil((lockedUntil - now) / (60 * 1000))
      flash.show(
        `El inicio de sesión está bloqueado. Inténtalo de nuevo en ${timeLeft} minutos.`,
        { cssClass: "alert-danger", timeout: 4000 }
      )
      return
    }

    this.authService.login(this.usuario).subscribe(
      (response) => {
        localStorage.setItem("jwt-token-veterinaria", response["token"])
        localStorage.removeItem("loginAttempts")
        localStorage.removeItem("lockedUntil")
        this.router.navigate(["/menu"])
      },
      (error) => {
        flash.show("Credenciales invalidas", {
          cssClass: "alert-danger",
          timeout: 1000,
        })
        localStorage.setItem(
          "loginAttempts",
          String(Math.min(attempts + 1, this.maxAttempts))
        )
        if (attempts + 1 >= this.maxAttempts) {
          localStorage.setItem("lockedUntil", String(now + this.lockoutTime))
        }
      }
    )
  }
}


