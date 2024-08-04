import { Component, OnInit } from '@angular/core'
import { NgForm } from '@angular/forms'
import { exportToExcel } from 'src/app/Functions/ExportExcel'
import { exportToPdf } from 'src/app/Functions/ExportPdf'
import { ShowAlert } from 'src/app/Functions/ShowAlert'
import { BoletaMapper } from 'src/app/Models/boleta'
import { BoletaService } from 'src/app/Services/boleta.service'

@Component({
  selector: 'app-boletas',
  templateUrl: './boletas.component.html',
  styleUrls: ['./boletas.component.css']
})
export class BoletasComponent implements OnInit{

  // Datos de listado
  boletas: BoletaMapper[] = []

  // Datos de paginacion
  page = 1

  // Datos de busqueda
  text:string = ''
  nameColum:string = 'cliente'
  isModified:boolean = false

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  // Datos de seleccion
  isSelect:Boolean = false

  // Datos de anulacion
  mensaje:string = ""
  id:number = 0

  constructor(
    private boletaService: BoletaService
  ){

  }

  ngOnInit(): void {
    this.loadListBoletas()
  }

  loadListBoletas(){
    this.boletaService.findAllMapper().subscribe(boletas => {
      this.boletas = boletas
    })
  }

  reloadFilterBoletas(nameColumn:string){
    this.nameColum = nameColumn
    this.isModified = ! this.isModified
  }

  anular(formulario:NgForm, cerrar:HTMLButtonElement,){
    if(!formulario.form.valid) return
    this.boletaService.anular(this.id, this.mensaje).subscribe( response => {
      this.showAlert.save(`Boleta #${this.id} anulada`)
      this.loadListBoletas()
    })
    cerrar.click()
  }

  descargar(id:number) {
    this.showAlert.question(`Deseas descargar Boleta N°${id}.pdf`, () => {
      this.boletaService.descargar(id).subscribe((pdf) => {
        const blob = new Blob([pdf], { type: 'application/pdf' })
        const url = window.URL.createObjectURL(blob)
        const link = document.createElement('a')
        link.href = url
        link.download = `Boleta N°${id}.pdf`
        link.click()
      })
    })
  }

  previsualizar(id:number){
    this.boletaService.previsualizar(id).subscribe(res => {
      let blob = new Blob([res], { type: 'application/pdf' });
      let url = window.URL.createObjectURL(blob);
      window.open(url, '_blank');
    });
  }

  exportarExcel(): void{
    this.showAlert.question("Deseas descargar Listado de Boletas.xlsx", () => {
      exportToExcel(this.boletas,"Listado de boletas")
    })
  }

  exportarPDF():void{
    exportToPdf("Listado de Boletas",this.boletas,['cliente', 'fecha', 'usuario','tipo_pago','total','estado'])
  }
}
