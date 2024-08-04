import { Component } from '@angular/core';
import { exportToExcel } from 'src/app/Functions/ExportExcel';
import { exportToPdf } from 'src/app/Functions/ExportPdf';
import { ShowAlert } from 'src/app/Functions/ShowAlert';
import { MascotaMapper } from 'src/app/Models/mascota';
import { MascotaService } from 'src/app/Services/mascota.service';

@Component({
  selector: 'app-mascotas',
  templateUrl: './mascotas.component.html',
  styleUrls: ['./mascotas.component.css']
})
export class MascotasComponent {

  // Datos de listado
  mascotas: MascotaMapper[] = []

  // Datos de paginacion
  page = 1

  // Datos de busqueda
  text:string = ''
  nameColum:string = 'nombre'
  isModified:boolean = false

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  constructor(
    private mascotaService: MascotaService
  ){}

  ngOnInit(): void {
    this.loadListMascotas()
  }

  loadListMascotas(){
    this.mascotaService.findAllMapper().subscribe(mascotas => {
      this.mascotas = mascotas;
    });
  }

  reloadFilterMascotas(nameColumn:string){
    this.nameColum = nameColumn
    this.isModified = ! this.isModified
  }

  exportarExcel(): void{
    this.showAlert.question("Deseas descargar Listado de Mascotas.xlsx", () => {
      exportToExcel(this.mascotas,"Listado de mascotas")
    })
  }

  exportarPDF():void{
    exportToPdf("Listado de Mascotas",this.mascotas,['nombre', 'cliente', 'fecha','sexo','especie','raza'])
  }
}


