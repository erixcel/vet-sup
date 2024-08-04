import { Component, ViewChild } from '@angular/core'
import { NgForm } from '@angular/forms'
import { ActivatedRoute, Router } from '@angular/router'
import { FlashMessagesComponent } from 'flash-messages-angular'
import { ShowAlert } from 'src/app/Functions/ShowAlert'
import { ModalTableComponent } from 'src/app/Interfaces/modal-table/modal-table.component'
import { Banio } from 'src/app/Models/banio'
import { Mascota } from 'src/app/Models/mascota'
import { BanioService } from 'src/app/Services/banio.service'
import { CitaService } from 'src/app/Services/cita.service'
import { MascotaService } from 'src/app/Services/mascota.service'

@Component({
  selector: 'app-form-banio',
  templateUrl: './form-banio.component.html',
  styleUrls: ['./form-banio.component.css']
})
export class FormBanioComponent {

  // Datos de inicializacion
  isEditar: boolean = false
  isCitado: boolean = false
  hasCitas: boolean = false
  idBanio: number = 0
  mascota_id: number | null = null
  cita_id: number | null = null
  title:string = "Registar Baño"
  banio: Banio = {
    tipo: "Baño Solo",
    precio: 0,
    detalles: "",
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
    private banioService: BanioService,
    private mascotaService: MascotaService,
    private citaService: CitaService,
  ) {
    this.mascota_id = this.router.getCurrentNavigation()?.extras.state?.['id']
    this.cita_id = this.router.getCurrentNavigation()?.extras.state?.['cita_id']
  }

  ngOnInit(): void {
    this.setFormBanio()
    this.loadForeneos()
  }

  setFormBanio(){
    this.route.params.subscribe(params => {
      this.isEditar = !!params['id']
      if (this.isEditar) {
        this.idBanio = params['id'] as number
        this.setBanio(this.idBanio)
      }
    })
  }

  setBanio(id:number) {
    this.banioService.findById(id).subscribe(banio => {
      this.banio = banio
      this.title = "Editar Baño"
      if(banio.cita != null) {
        this.isCitado = true
        this.cita_id = banio.cita.id!
      }
      this.configModalTable()
    })
  }

  loadForeneos(){
    if ( this.mascota_id != null) this.mascotaService.findById(this.mascota_id).subscribe(mascota => {
        this.banio.mascota = mascota
    })
    if (this.cita_id != null) this.citaService.findById(this.cita_id).subscribe(cita => {
      this.banio.cita = cita
      this.isCitado = true
      this.configModalTable()
    })
  }

  configModalTable(){
    this.citaService.findAllByMascotaId(this.banio.mascota!.id!).subscribe(citasMapper => {
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
        this.banio.cita = cita
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

    if (!this.isCitado) this.banio.cita = undefined

    if (this.isEditar) {
      this.banioService.update(this.banio.id!, this.banio).subscribe(banio => {
        this.router.navigate(['/menu/banios'])
        this.showAlert.save("Baño Actualizado!")
      })
    } else {
      this.banioService.insert(this.banio).subscribe(banio => {
        this.router.navigate(['/menu/banios'])
        this.showAlert.save("Baño Registrado!")
      })
    }
  }

  delete(){
    this.showAlert.delete("¿Seguro que deseas eliminar el baño?",() => {
      this.banioService.delete(this.idBanio).subscribe(response => {
        this.router.navigate(['/menu/banios'])
      })
    },"Baño Eliminado!","Ningun Baño Eliminado!")
  }
}
