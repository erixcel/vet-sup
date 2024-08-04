import { Component } from '@angular/core';
import { exportToExcel } from 'src/app/Functions/ExportExcel';
import { exportToPdf } from 'src/app/Functions/ExportPdf';
import { ShowAlert } from 'src/app/Functions/ShowAlert';
import { BanioMapper } from 'src/app/Models/banio';
import { BanioService } from 'src/app/Services/banio.service';

@Component({
  selector: 'app-banos',
  templateUrl: './banios.component.html',
  styleUrls: ['./banios.component.css']
})
export class BaniosComponent {
  // Datos de listado
  banios: BanioMapper[] = []

  // Datos de paginacion
  page = 1

  // Datos de busqueda
  text:string = ''
  nameColum:string = 'mascota'
  isModified:boolean = false

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  constructor(
    private banioService: BanioService
  ){}

  ngOnInit(): void {
    this.loadListBanios()
  }

  loadListBanios(){
    this.banioService.findAllMapper().subscribe(banios => {
      this.banios = banios;
    });
  }

  reloadFilterBanios(nameColumn:string){
    this.nameColum = nameColumn
    this.isModified = ! this.isModified
  }

  exportarExcel(): void{
    this.showAlert.question("Deseas descargar Listado de Banios.xlsx", () => {
      exportToExcel(this.banios,"Listado de banios")
    })
  }

  exportarPDF():void{
    exportToPdf("Listado de Banios",this.banios,['nombre', 'apellidos', 'celular','direccion'])
  }
}
