import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CargarCSS } from 'src/app/Functions/CargarCSS';
import { CargarJS } from 'src/app/Functions/CargarJS';
import { AuthService } from 'src/app/Services/auth.service';

@Component({

  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit{

  isRecepcionista:Boolean = false
  isVeterinario:Boolean = false
  isGroomer:Boolean = false
  isAlmacenero:Boolean = false
  isAdministrador:Boolean = false

  constructor(
    private cargarJS:CargarJS,
    private cargarCSS:CargarCSS,
    private router: Router,
    private authService: AuthService,
  ){
      cargarCSS.Carga(["menu"])
      cargarJS.Carga(["menu"])
  }
  ngOnInit(): void {
    this.authService.usuario().subscribe(usuario => {
      if(usuario?.roles != null){
        for(let rol of usuario.roles){
          if(rol.nombre === "RECEPCIONISTA") this.isRecepcionista = true
          if(rol.nombre === "VETERINARIO") this.isVeterinario = true
          if(rol.nombre === "GROOMER") this.isGroomer = true
          if(rol.nombre === "ALMACENERO") this.isAlmacenero = true
          if(rol.nombre === "ADMINISTRADOR") this.isAdministrador = true
        }
      }
    })
  }

  logout(){
    localStorage.removeItem('jwt-token-veterinaria')
    this.router.navigate(['/login'])
  }

}
