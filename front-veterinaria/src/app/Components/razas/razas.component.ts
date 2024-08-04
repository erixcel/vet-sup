import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { FlashMessagesComponent } from 'flash-messages-angular';
import { exportToExcel } from 'src/app/Functions/ExportExcel';
import { exportToPdf } from 'src/app/Functions/ExportPdf';
import { ShowAlert } from 'src/app/Functions/ShowAlert';
import { Especie } from 'src/app/Models/especie';
import { Raza, RazaMapper } from 'src/app/Models/raza';
import { EspecieService } from 'src/app/Services/especie.service';
import { RazaService } from 'src/app/Services/raza.service';

@Component({
  selector: 'app-razas',
  templateUrl: './razas.component.html',
  styleUrls: ['./razas.component.css']
})
export class RazasComponent implements OnInit{

  // Datos de listado
  razas: RazaMapper[] = []

  // Datos de paginacion
  page = 1

  // Datos de busqueda
  text:string = ''
  nameColum:string = 'nombre'
  isModified:boolean = false

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  // Datos de registro o edicion
  raza:Raza = {especie:{nombre:""}}
  isEditar:boolean = false
  titleForm:string = "Registrar Raza"
  textBottonSave:string = "Guardar registro"
  especies:Especie[] = []

  constructor(
    private router:Router,
    private razaService: RazaService,
    private especieService: EspecieService
  ){

  }

  ngOnInit() {
    this.loadListRazas()
    this.loadForaneos()
  }

  loadListRazas(){
    this.razaService.findAllMapper().subscribe(razas => {
      this.razas = razas
    })
  }

  loadForaneos(){
    this.especieService.findAll().subscribe(especies => {
      this.especies = especies
      this.raza.especie = especies[0]
    })
  }

  setRaza(id:number){
    this.razaService.findById(id).subscribe(raza => {
      this.raza = raza
      this.isEditar = true
      this.titleForm = "Editar Raza"
      this.textBottonSave = "Actualizar registro"
    })
  }

  cleanRaza(formulario:NgForm){
    this.raza = {especie:{nombre:""}}
    this.isEditar = false
    this.titleForm = "Registrar Raza"
    this.textBottonSave = "Guardar registro"
    this.loadForaneos()
    formulario.resetForm()
  }

  saveRaza(formulario:NgForm, flash:FlashMessagesComponent){

    if(! formulario.form.valid){
      flash.show('Llena correctamente el formulario',
      {cssClass: 'alert-danger-config', timeout: 4000})
      return
    }

    if(this.raza.id !== undefined) this.razaService.update(this.raza.id!,this.raza).subscribe( response =>{
      this.showAlert.save("Raza Actualizada")
      this.loadListRazas()
      this.cleanRaza(formulario)
    })
    else this.razaService.insert(this.raza).subscribe( response => {
      this.showAlert.save("Raza Registrada")
      this.loadListRazas()
      this.cleanRaza(formulario)
    })
  }

  deleteRaza(id:number){
    this.showAlert.delete("¿Seguro que deseas eliminar esta raza?",() => {
      this.razaService.delete(id).subscribe(response => {
        this.loadListRazas()
      })
    },"Raza Eliminada!","Ninguna Raza Eliminada!")
  }

  reloadFilterRazas(nameColumn:string){
    this.nameColum = nameColumn
    this.isModified = ! this.isModified
  }

  openFormBoleta(id:number | undefined){
    if( id != undefined) localStorage.setItem('raza-select-vet',id.toString())
    this.router.navigate(['/menu/boletas/registrar'])
  }

  exportarExcel(): void{
    this.showAlert.question("Deseas descargar Listado de Razas.xlsx", () => {
      exportToExcel(this.razas,"Listado de Razas")
    })
  }

  exportarPDF():void{
    exportToPdf("Listado de Razas",this.razas,['id','nombre','especie'])
  }
}
