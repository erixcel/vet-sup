import { Component } from '@angular/core';
import { exportToExcel } from 'src/app/Functions/ExportExcel';
import { exportToPdf } from 'src/app/Functions/ExportPdf';
import { ShowAlert } from 'src/app/Functions/ShowAlert';
import { TratamientoMapper } from 'src/app/Models/tratamiento';
import { TratamientoService } from 'src/app/Services/tratamiento.service';

@Component({
  selector: 'app-tratamientos',
  templateUrl: './tratamientos.component.html',
  styleUrls: ['./tratamientos.component.css']
})
export class TratamientosComponent {

  // Datos de listado
  tratamientos: TratamientoMapper[] = []

  // Datos de paginacion
  page = 1

  // Datos de busqueda
  text:string = ''
  nameColum:string = 'mascota'
  isModified:boolean = false

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  constructor(
    private tratamientoService: TratamientoService
  ){}

  ngOnInit(): void {
    this.loadListTratamientos()
  }

  loadListTratamientos(){
    this.tratamientoService.findAllMapper().subscribe(tratamientos => {
      this.tratamientos = tratamientos;
    });
  }

  reloadFilterTratamientos(nameColumn:string){
    this.nameColum = nameColumn
    this.isModified = ! this.isModified
  }

  exportarExcel(): void{
    this.showAlert.question("Deseas descargar Listado de Clientes.xlsx", () => {
      exportToExcel(this.tratamientos,"Listado de tratamientos")
    })
  }

  exportarPDF():void{
    exportToPdf("Listado de Clientes",this.tratamientos,['nombre', 'apellidos', 'celular','direccion'])
  }
}
