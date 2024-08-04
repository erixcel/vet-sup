import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { FlashMessagesComponent } from 'flash-messages-angular';
import { exportToExcel } from 'src/app/Functions/ExportExcel';
import { exportToPdf } from 'src/app/Functions/ExportPdf';
import { ShowAlert } from 'src/app/Functions/ShowAlert';
import { Categoria, CategoriaMapper } from 'src/app/Models/categoria';
import { CategoriaService } from 'src/app/Services/categoria.service';

@Component({
  selector: 'app-categorias',
  templateUrl: './categorias.component.html',
  styleUrls: ['./categorias.component.css']
})
export class CategoriasComponent implements OnInit{
  // Datos de listado
  categorias: CategoriaMapper[] = []

  // Datos de paginacion
  page = 1

  // Datos de busqueda
  text:string = ''
  nameColum:string = 'nombre'
  isModified:boolean = false

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  // Datos de registro o edicion
  categoria:Categoria = {}
  isEditar:boolean = false
  titleForm:string = "Registrar Categoria"
  textBottonSave:string = "Guardar registro"

  constructor(
    private router:Router,
    private categoriaService: CategoriaService
  ){

  }

  ngOnInit() {
    this.loadListCategorias()
  }

  loadListCategorias(){
    this.categoriaService.findAllMapper().subscribe(categorias => {
      this.categorias = categorias
    })
  }

  setCategoria(id:number){
    this.categoriaService.findById(id).subscribe(categoria => {
      this.categoria = categoria
      this.isEditar = true
      this.titleForm = "Editar Categoria"
      this.textBottonSave = "Actualizar registro"
    })
  }

  cleanCategoria(formulario:NgForm){
    this.categoria = {}
    this.isEditar = false
    this.titleForm = "Registrar Categoria"
    this.textBottonSave = "Guardar registro"
    formulario.resetForm()
  }

  saveCategoria(formulario:NgForm, flash:FlashMessagesComponent){

    if(! formulario.form.valid){
      flash.show('Llena correctamente el formulario',
      {cssClass: 'alert-danger-config', timeout: 4000})
      return
    }

    if(this.categoria.id !== undefined) this.categoriaService.update(this.categoria.id!,this.categoria).subscribe( response =>{
      this.showAlert.save("Categoria Actualizada")
      this.loadListCategorias()
      this.cleanCategoria(formulario)
    })
    else this.categoriaService.insert(this.categoria).subscribe( response => {
      this.showAlert.save("Categoria Registrada")
      this.loadListCategorias()
      this.cleanCategoria(formulario)
    })
  }

  deleteCategoria(id:number){
    this.showAlert.delete("¿Seguro que deseas eliminar esta categoria?",() => {
      this.categoriaService.delete(id).subscribe(response => {
        this.loadListCategorias()
      })
    },"Categoria Eliminada!","Ninguna Categoria Eliminada!")
  }

  reloadFilterCategorias(nameColumn:string){
    this.nameColum = nameColumn
    this.isModified = ! this.isModified
  }

  openFormBoleta(id:number | undefined){
    if( id != undefined) localStorage.setItem('categoria-select-vet',id.toString())
    this.router.navigate(['/menu/boletas/registrar'])
  }

  exportarExcel(): void{
    this.showAlert.question("Deseas descargar Listado de Categorias.xlsx", () => {
      exportToExcel(this.categorias,"Listado de Categorias")
    })
  }

  exportarPDF():void{
    exportToPdf("Listado de Categorias",this.categorias,['id','nombre'])
  }
}
