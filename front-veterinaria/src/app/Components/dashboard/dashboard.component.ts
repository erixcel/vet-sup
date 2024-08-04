import { Component, OnInit } from '@angular/core'
import { getDateNow } from 'src/app/Functions/Generales'
import { ShowAlert } from 'src/app/Functions/ShowAlert'
import { Usuario } from 'src/app/Models/usuario'
import { AuthService } from 'src/app/Services/auth.service'
import { BackupService } from 'src/app/Services/backup.service'
import { PanelService } from 'src/app/Services/panel.service'

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{

  // Datos a mostrar
  total_tratamientos: number = 0
  total_clientes: number = 0
  total_mascotas: number = 0
  total_banios: number = 0
  total_citas: number = 0
  total_boletas: number = 0
  total_roles: number = 0

  // Usuario logueado
  user: Usuario = {username: ""}

  // Datos de authorizacion
  isAdmin:boolean = false

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  constructor(
    private panelService: PanelService,
    private authService: AuthService,
    private backupService: BackupService,
  ){

  }
  ngOnInit(): void {
    this.panelService.total_tratamientos().subscribe( response => {
      this.total_tratamientos = response['total']
    })
    this.panelService.total_clientes().subscribe( response => {
      this.total_clientes = response['total']
    })
    this.panelService.total_mascotas().subscribe( response => {
      this.total_mascotas = response['total']
    })
    this.panelService.total_banios().subscribe( response => {
      this.total_banios = response['total']
    })
    this.panelService.total_citas().subscribe( response => {
      this.total_citas = response['total']
    })
    this.panelService.total_boletas().subscribe( response => {
      this.total_boletas = response['total']
    })
    this.authService.usuario().subscribe( usuario => {
      this.user = usuario
      this.total_roles = usuario.roles?.length as number
      if(usuario?.roles?.length === 5) this.isAdmin = true
    })

  }

  downloadBackup(): void {

    this.showAlert.question("¿Quieres descargar una copia de seguridad?", () => {
      this.backupService.downloadBackup().subscribe(blob => {

        const url = URL.createObjectURL(blob)

        const a = document.createElement('a')
        a.href = url
        a.download = `backup ${getDateNow()}.sql`
        document.body.appendChild(a)
        a.click()
        document.body.removeChild(a)

        URL.revokeObjectURL(url)
      })
    })
  }

}
