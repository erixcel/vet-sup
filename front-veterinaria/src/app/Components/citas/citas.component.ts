import { Component, OnInit } from '@angular/core'
import { NgForm } from '@angular/forms'
import { Router } from '@angular/router'
import { exportToExcel } from 'src/app/Functions/ExportExcel'
import { exportToPdf } from 'src/app/Functions/ExportPdf'
import { ShowAlert } from 'src/app/Functions/ShowAlert'
import { CitaMapper } from 'src/app/Models/cita'
import { AuthService } from 'src/app/Services/auth.service'
import { CitaService } from 'src/app/Services/cita.service'

@Component({
  selector: 'app-citas',
  templateUrl: './citas.component.html',
  styleUrls: ['./citas.component.css']
})
export class CitasComponent implements OnInit{

  // Datos de listado
  citas: CitaMapper[] = []

  // Datos de paginacion
  page = 1

  // Datos de busqueda
  text:string = ''
  nameColum:string = 'cliente'
  isModified:boolean = false

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  // Datos de cancelacion y atencion
  id:number = 0
  mensaje:string = ""

  // Datos de proteccion
  isVeterinario:Boolean = false
  isGroomer:Boolean = false

  constructor(
    private router:Router,
    private citaService: CitaService,
    private authService: AuthService
  ){

  }

  ngOnInit(): void {
    this.loadListCitas("todas")
    this.setRoles()
  }

  loadListCitas(estado:string){
    if(estado === "todas"){
      this.citaService.findAllMapper().subscribe(citas => {
        this.citas = citas
      })
    } else {
      this.citaService.findAllByEstado(estado).subscribe(citas => {
        if(citas == null) this.citas = []
        else this.citas = citas
      })
    }

  }

  reloadFilterCitas(nameColumn:string){
    this.nameColum = nameColumn
    this.isModified = ! this.isModified
  }

  setRoles(){
    this.authService.usuario().subscribe(usuario => {
      if(usuario?.roles != null){
        for(let rol of usuario.roles){
          if(rol.nombre === "VETERINARIO") this.isVeterinario = true
          if(rol.nombre === "GROOMER") this.isGroomer = true
        }
      }
    })
  }

  cancelar(formulario:NgForm, cerrar:HTMLButtonElement, estado:string){
    if(!formulario.form.valid) return
    this.citaService.cancelar(this.id, this.mensaje).subscribe( response => {
      this.showAlert.save(`Cita #${this.id} cancelada`)
      estado = estado.toLocaleLowerCase().trim()
      this.loadListCitas(estado)
    })
    cerrar.click()
  }

  openFormRegisterTratamiento(cita_id:number,cerrar:HTMLButtonElement){
    this.citaService.findById(cita_id).subscribe(cita => {
      const id:number = cita.mascota!.id!
      cerrar.click()
      this.router.navigate(['/menu/tratamientos/registrar'], { state: { id , cita_id } })
    })

  }

  openFormRegisterBanio(cita_id:number,cerrar:HTMLButtonElement){
    this.citaService.findById(cita_id).subscribe(cita => {
      const id:number = cita.mascota!.id!
      cerrar.click()
      this.router.navigate(['/menu/banios/registrar'], { state: { id , cita_id } })
    })
  }

  exportarExcel(): void{
    this.showAlert.question("Deseas descargar Listado de Citas.xlsx", () => {
      exportToExcel(this.citas,"Listado de citas")
    })
  }

  exportarPDF():void{
    exportToPdf("Listado de Citas",this.citas,['cliente', 'celular', 'mascota', 'fecha_programada','fecha_atendida','estado'])
  }
}
