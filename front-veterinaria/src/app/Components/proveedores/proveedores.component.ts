import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { FlashMessagesComponent } from 'flash-messages-angular';
import { exportToExcel } from 'src/app/Functions/ExportExcel';
import { exportToPdf } from 'src/app/Functions/ExportPdf';
import { setImageWithCompressionAndResize } from 'src/app/Functions/FunctionsImage';
import { ShowAlert } from 'src/app/Functions/ShowAlert';
import { Proveedor, ProveedorMapper } from 'src/app/Models/proveedor';
import { ProveedorService } from 'src/app/Services/proveedor.service';

@Component({
  selector: 'app-proveedores',
  templateUrl: './proveedores.component.html',
  styleUrls: ['./proveedores.component.css']
})
export class ProveedoresComponent implements OnInit{

  // Datos de listado
  proveedores: ProveedorMapper[] = []

  // Datos de paginacion
  page = 1

  // Datos de busqueda
  text:string = ''
  nameColum:string = 'nombre'
  isModified:boolean = false

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  // Datos de registro o edicion
  proveedor:Proveedor = {}
  isEditar:boolean = false
  fotoProveedor: File | null = null
  titleForm:string = "Registrar Proveedor"
  textBottonSave:string = "Guardar registro"

  // Datos del html
  @ViewChild('proveedorForm') proveedorForm: NgForm

  constructor(
    private router:Router,
    private proveedorService: ProveedorService
  ){

  }

  ngOnInit() {
    this.loadListProveedores()
  }

  loadListProveedores(){
    this.proveedorService.findAllMapper().subscribe(proveedores => {
      this.proveedores = proveedores
    })
  }

  setProveedor(id:number){
    this.proveedorService.findById(id).subscribe(proveedor => {
      this.proveedor = proveedor
      this.isEditar = true
      this.titleForm = "Editar Proveedor"
      this.textBottonSave = "Actualizar registro"
    })
  }

  cleanProveedor(){
    this.proveedor = {}
    this.isEditar = false
    this.fotoProveedor = null
    this.titleForm = "Registrar Proveedor"
    this.textBottonSave = "Guardar registro"
    this.proveedorForm.resetForm()
  }

  afterSaveProveedor(){
    const status = this.isEditar ? 'Actualizado' : 'Registrado'
    this.router.navigate(['/menu/proveedores'])
    this.showAlert.save(`Proveedor ${status}!`)
    this.loadListProveedores()
    this.cleanProveedor()
  }

  saveProveedor(formulario:NgForm, flash:FlashMessagesComponent): void {
    if(! formulario.form.valid){
      flash.show('Llena correctamente el formulario',
      {cssClass: 'alert-danger-config', timeout: 4000})
      return
    }

    if (this.isEditar) {
      this.proveedorService.update(this.proveedor.id!, this.proveedor).subscribe(response => {
        if(this.fotoProveedor != null) {
          this.proveedorService.setFoto(response.id as number,this.fotoProveedor).subscribe(response => {
            this.afterSaveProveedor()
          })
          this.showAlert.loading()
        } else {
          this.afterSaveProveedor()
        }
      })
    } else {
      console.log(this.proveedor.foto)
      this.proveedorService.insert(this.proveedor).subscribe(response => {
        if(this.fotoProveedor != null) {
          this.proveedorService.setFoto(response.id as number,this.fotoProveedor).subscribe(response => {
            this.afterSaveProveedor()
          })
          this.showAlert.loading()
      } else {
        this.afterSaveProveedor()
        }
      })
    }
  }

  deleteProveedor(id:number){
    this.showAlert.delete("¿Seguro que deseas eliminar este proveedor?",() => {
      this.proveedorService.delete(id).subscribe(response => {
        this.loadListProveedores()
      })
    },"Proveedor Eliminado!","Ningun Proveedor Eliminado!")
  }

  reloadFilterProveedores(nameColumn:string){
    this.nameColum = nameColumn
    this.isModified = ! this.isModified
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement
    if (input.files) {
      setImageWithCompressionAndResize(input.files).then(({ file, src }) => {
        this.fotoProveedor = file
        this.proveedor.foto = src
      });
    }
  }

  openFormBoleta(id:number | undefined){
    if( id != undefined) localStorage.setItem('proveedor-select-vet',id.toString())
    this.router.navigate(['/menu/boletas/registrar'])
  }

  exportarExcel(): void{
    this.showAlert.question("Deseas descargar Listado de Proveedores.xlsx", () => {
      exportToExcel(this.proveedores,"Listado de Proveedores")
    })
  }

  exportarPDF():void{
    exportToPdf("Listado de Proveedores",this.proveedores,['id','nombre','celular','correo'])
  }
}
