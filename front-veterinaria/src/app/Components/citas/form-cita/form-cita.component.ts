import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FlashMessagesComponent } from 'flash-messages-angular';
import { ShowAlert } from 'src/app/Functions/ShowAlert';
import { Mascota } from 'src/app/Models/mascota';
import { Cita } from 'src/app/Models/cita';
import { MascotaService } from 'src/app/Services/mascota.service';
import { CitaService } from 'src/app/Services/cita.service';

@Component({
  selector: 'app-form-cita',
  templateUrl: './form-cita.component.html',
  styleUrls: ['./form-cita.component.css']
})
export class FormCitaComponent {

  // Datos de inicializacion
  isEditar: boolean = false
  idCita: number = 0
  mascota_id: number | null = null
  title:string = "Registar Cita"
  cita: Cita = {
    estado: "pendiente",
    motivo: "Consulta",
    fecha_programada: new Date(new Date().setDate(new Date().getDate() + 1)).toISOString().slice(0, -8),
    mascota: {}
  }

  // Datos de foraneos
  mascotas: Mascota[] = []

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  constructor(
    private route: ActivatedRoute,
    private router:Router,
    private citaService: CitaService,
    private mascotaService: MascotaService,
  ) {
    this.mascota_id = this.router.getCurrentNavigation()?.extras.state?.['id'];
  }

  ngOnInit(): void {
    this.setFormCita()
    this.loadForeneos()
  }

  setFormCita(){
    this.route.params.subscribe(params => {
      this.isEditar = !!params['id']
      if (this.isEditar) {
        this.idCita = params['id'] as number
        this.setCita(this.idCita)
      }
    });
  }

  setCita(id:number) {
    this.citaService.findById(id).subscribe(cita => {
      this.cita = cita
      this.title = "Editar Cita"
    });
  }

  loadForeneos(){
    this.mascotaService.findAll().subscribe( mascotas => {
      this.mascotas = mascotas
      if ( this.mascota_id != null) this.mascotaService.findById(this.mascota_id).subscribe(mascota => {
        this.cita.mascota = mascota
      })
    })
  }

  save(formulario:NgForm, flash:FlashMessagesComponent): void {
    if(! formulario.form.valid){
      flash.show('Llena correctamente el formulario',
      {cssClass: 'alert-danger-config', timeout: 4000})
      return
    }

    if (this.isEditar) {
      this.citaService.update(this.cita.id!, this.cita).subscribe(cita => {
        this.router.navigate(['/menu/citas'])
        this.showAlert.save("Cita Actualizada!")
      })
    } else {
      this.citaService.insert(this.cita).subscribe(cita => {
        this.router.navigate(['/menu/citas'])
        this.showAlert.save("Cita Actualizada!")
      })
    }
  }

  delete(){
    this.showAlert.delete("¿Seguro que deseas eliminar el cita?",() => {
      this.citaService.delete(this.idCita).subscribe(response => {
        this.router.navigate(['/menu/citas'])
      })
    },"Cita Eliminada!","Ninguna Cita Eliminada!")
  }
}
