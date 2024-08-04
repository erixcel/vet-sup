import { MascotaService } from 'src/app/Services/mascota.service';
import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FlashMessagesComponent } from 'flash-messages-angular';
import { ShowAlert } from 'src/app/Functions/ShowAlert';
import { Mascota } from 'src/app/Models/mascota';
import { Tratamiento } from 'src/app/Models/tratamiento';
import { TratamientoService } from 'src/app/Services/tratamiento.service';
import { CitaService } from 'src/app/Services/cita.service';
import { ModalTableComponent } from 'src/app/Interfaces/modal-table/modal-table.component';

@Component({
  selector: 'app-form-tratamiento',
  templateUrl: './form-tratamiento.component.html',
  styleUrls: ['./form-tratamiento.component.css']
})
export class FormTratamientoComponent {

  // Datos de inicializacion
  isEditar: boolean = false
  isCitado: boolean = false
  hasCitas: boolean = false
  idTratamiento: number = 0
  mascota_id: number | null = null
  cita_id: number | null = null
  title:string = "Registar Tratamiento"
  tratamiento: Tratamiento = {
    tipo: "Consulta",
    precio: 0,
    descripcion: "",
    mascota: {}
  }

  // Datos de foraneos
  mascotas: Mascota[] = []

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  // Referencia tabla modal
  @ViewChild('modalTable') modalTable: ModalTableComponent

  constructor(
    private route: ActivatedRoute,
    private router:Router,
    private tratamientoService: TratamientoService,
    private mascotaService: MascotaService,
    private citaService: CitaService,
  ) {
    this.mascota_id = this.router.getCurrentNavigation()?.extras.state?.['id'];
    this.cita_id = this.router.getCurrentNavigation()?.extras.state?.['cita_id'];
  }

  ngOnInit(): void {
    this.setFormTratamiento()
    this.loadForeneos()
  }

  setFormTratamiento(){
    this.route.params.subscribe(params => {
      this.isEditar = !!params['id']
      if (this.isEditar) {
        this.idTratamiento = params['id'] as number
        this.setTratamiento(this.idTratamiento)
      }
    });
  }

  setTratamiento(id:number) {
    this.tratamientoService.findById(id).subscribe(tratamiento => {
      this.tratamiento = tratamiento
      this.title = "Editar Tratamiento"
      if(tratamiento.cita != null) {
        this.isCitado = true
        this.cita_id = tratamiento.cita.id!
      }
      this.configModalTable()
    });
  }

  loadForeneos(){
    if (this.mascota_id != null) this.mascotaService.findById(this.mascota_id).subscribe(mascota => {
      this.tratamiento.mascota = mascota
    })

    if (this.cita_id != null) this.citaService.findById(this.cita_id).subscribe(cita => {
      this.tratamiento.cita = cita
      this.isCitado = true
      this.configModalTable()
    })
  }

  configModalTable(){
    this.citaService.findAllByMascotaId(this.tratamiento.mascota!.id!).subscribe(citasMapper => {
      if (citasMapper != null){
        citasMapper = citasMapper.filter(cita => cita.id === this.cita_id || cita.estado === "pendiente")
        if (citasMapper.length > 0) this.hasCitas = true
      }
      this.modalTable.setTitle("Seleccionar Cita")
      this.modalTable.setList(citasMapper)
      this.modalTable.setIdSelect(this.cita_id + "")
      this.modalTable.setListNamesColumns(["Motivo","Estado","Fecha Programada","Fecha Atendida"])
      this.modalTable.setListNamesAtributes(["motivo","estado","fecha_programada","fecha_atendida"])
    })
  }

  onCitaChange(event:any){
    if (event.target.checked) {
      this.modalTable.open()
      this.isCitado = true
    } else {
      this.isCitado = false
    }
  }

  onModalTableClose(){
    const switchElement = document.querySelector('#cita') as HTMLInputElement
    switchElement.checked = false
    switchElement.dispatchEvent(new Event('input', { bubbles: true }))
  }

  onModalTableCorrect(){
    const id = parseInt(this.modalTable.getIdSelect())
    if(!isNaN(id)) {
      this.citaService.findById(id).subscribe( cita => {
        this.tratamiento.cita = cita
      })
      this.modalTable.hide()
    }
  }

  save(formulario:NgForm, flash:FlashMessagesComponent): void {
    if(! formulario.form.valid){
      flash.show('Llena correctamente el formulario',
      {cssClass: 'alert-danger-config', timeout: 4000})
      return
    }

    if (!this.isCitado) this.tratamiento.cita = undefined

    if (this.isEditar) {
      this.tratamientoService.update(this.tratamiento.id!, this.tratamiento).subscribe(tratamiento => {
        this.router.navigate(['/menu/tratamientos'])
        this.showAlert.save("Tratamiento Actualizado!")
      })
    } else {
      this.tratamientoService.insert(this.tratamiento).subscribe(tratamiento => {
        this.router.navigate(['/menu/tratamientos'])
        this.showAlert.save("Tratamiento Actualizado!")
      })
    }
  }

  delete(){
    this.showAlert.delete("¿Seguro que deseas eliminar el tratamiento?",() => {
      this.tratamientoService.delete(this.idTratamiento).subscribe(response => {
        this.router.navigate(['/menu/tratamientos'])
      })
    },"Tratamiento Eliminado!","Ningun Tratamiento Eliminado!")
  }
}
