import { ShowAlert } from 'src/app/Functions/ShowAlert'
import { Component, OnInit } from '@angular/core'
import { exportToExcel } from 'src/app/Functions/ExportExcel'
import { exportToPdf } from 'src/app/Functions/ExportPdf'
import { ActivatedRoute, Router } from '@angular/router'
import { EspecieService } from 'src/app/Services/especie.service'
import { Especie, EspecieMapper } from 'src/app/Models/especie'
import { NgForm } from '@angular/forms'
import { FlashMessagesComponent } from 'flash-messages-angular'

@Component({
  selector: 'app-especies',
  templateUrl: './especies.component.html',
  styleUrls: ['./especies.component.css']
})
export class EspeciesComponent implements OnInit{

  // Datos de listado
  especies: EspecieMapper[] = []

  // Datos de paginacion
  page = 1

  // Datos de busqueda
  text:string = ''
  nameColum:string = 'nombre'
  isModified:boolean = false

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  // Datos de registro o edicion
  especie:Especie = {}
  isEditar:boolean = false
  titleForm:string = "Registrar Especie"
  textBottonSave:string = "Guardar registro"

  constructor(
    private router:Router,
    private especieService: EspecieService
  ){

  }

  ngOnInit() {
    this.loadListEspecies()
  }


  loadListEspecies(){
    this.especieService.findAllMapper().subscribe(especies => {
      this.especies = especies
    })
  }

  setEspecie(id:number){
    this.especieService.findById(id).subscribe(especie => {
      this.especie = especie
      this.isEditar = true
      this.titleForm = "Editar Especie"
      this.textBottonSave = "Actualizar registro"
    })
  }

  cleanEspecie(formulario:NgForm){
    this.especie = {}
    this.isEditar = false
    this.titleForm = "Registrar Especie"
    this.textBottonSave = "Guardar registro"
    formulario.resetForm()
  }

  saveEspecie(formulario:NgForm, flash:FlashMessagesComponent){

    if(! formulario.form.valid){
      flash.show('Llena correctamente el formulario',
      {cssClass: 'alert-danger-config', timeout: 4000})
      return
    }

    if(this.especie.id !== undefined) this.especieService.update(this.especie.id!,this.especie).subscribe( response =>{
      this.showAlert.save("Especie Actualizada")
      this.loadListEspecies()
      this.cleanEspecie(formulario)
    })
    else this.especieService.insert(this.especie).subscribe( response => {
      this.showAlert.save("Especie Registrada")
      this.loadListEspecies()
      this.cleanEspecie(formulario)
    })
  }

  deleteEspecie(id:number){
    this.showAlert.delete("¿Seguro que deseas eliminar esta especie?",() => {
      this.especieService.delete(id).subscribe(response => {
        this.loadListEspecies()
      })
    },"Especie Eliminada!","Ninguna Especie Eliminada!")
  }

  reloadFilterEspecies(nameColumn:string){
    this.nameColum = nameColumn
    this.isModified = ! this.isModified
  }

  openFormBoleta(id:number | undefined){
    if( id != undefined) localStorage.setItem('especie-select-vet',id.toString())
    this.router.navigate(['/menu/boletas/registrar'])
  }

  exportarExcel(): void{
    this.showAlert.question("Deseas descargar Listado de Especies.xlsx", () => {
      exportToExcel(this.especies,"Listado de Especies")
    })
  }

  exportarPDF():void{
    exportToPdf("Listado de Especies",this.especies,['id','nombre'])
  }
}
