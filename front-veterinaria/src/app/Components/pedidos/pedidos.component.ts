import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { exportToExcel } from 'src/app/Functions/ExportExcel';
import { exportToPdf } from 'src/app/Functions/ExportPdf';
import { formatNumber } from 'src/app/Functions/Generales';
import { ShowAlert } from 'src/app/Functions/ShowAlert';
import { PedidoMapper } from 'src/app/Models/pedido';
import { PedidoService } from 'src/app/Services/pedido.service';

@Component({
  selector: 'app-pedidos',
  templateUrl: './pedidos.component.html',
  styleUrls: ['./pedidos.component.css']
})
export class PedidosComponent implements OnInit{

  // Datos de listado
  pedidos: PedidoMapper[] = []

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
    private pedidoService: PedidoService
  ){

  }

  ngOnInit(): void {
    this.loadListPedidoss()
  }

  loadListPedidoss(){
    this.pedidoService.findAllMapper().subscribe(pedidos => {
      this.pedidos = pedidos
    })
  }

  reloadFilterPedidos(nameColumn:string){
    this.nameColum = nameColumn
    this.isModified = ! this.isModified
  }

  anular(formulario:NgForm, cerrar:HTMLButtonElement,){
    if(!formulario.form.valid) return
    this.pedidoService.anular(this.id, this.mensaje).subscribe( response => {
      this.showAlert.save(`Orden #${formatNumber(this.id)} anulada`)
      this.loadListPedidoss()
    })
    cerrar.click()
  }

  recibir(id:number){
    this.showAlert.question(`Deseas confirmar la Orden #${id}`, () => {
      this.pedidoService.recibir(id).subscribe( response => {
        this.loadListPedidoss()
      })
    })
  }

  descargar(id:number) {
    this.showAlert.question(`Deseas descargar Orden N°${id}.pdf`, () => {
      this.pedidoService.descargar(id).subscribe((pdf) => {
        const blob = new Blob([pdf], { type: 'application/pdf' })
        const url = window.URL.createObjectURL(blob)
        const link = document.createElement('a')
        link.href = url
        link.download = `Orden N°${id}.pdf`
        link.click()
      })
    })
  }

  previsualizar(id:number){
    this.pedidoService.previsualizar(id).subscribe(res => {
      let blob = new Blob([res], { type: 'application/pdf' });
      let url = window.URL.createObjectURL(blob);
      window.open(url, '_blank');
    });
  }

  exportarExcel(): void{
    this.showAlert.question("Deseas descargar Ordenes de Compra.xlsx", () => {
      exportToExcel(this.pedidos,"Ordenes de Compra")
    })
  }

  exportarPDF():void{
    exportToPdf("Ordenes de Compra",this.pedidos,['proveedor', 'fecha_emision','fecha_entrega', 'usuario','tipo_pago','total','estado'])
  }
}
