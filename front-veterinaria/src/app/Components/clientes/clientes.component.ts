import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { exportToExcel } from 'src/app/Functions/ExportExcel';
import { exportToPdf } from 'src/app/Functions/ExportPdf';
import { ShowAlert } from 'src/app/Functions/ShowAlert';
import { ClienteMapper } from 'src/app/Models/cliente';
import { ClienteService } from 'src/app/Services/cliente.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit{

  // Datos de listado
  clientes: ClienteMapper[] = []

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
  title:string = "Panel de Clientes"

  constructor(
    private router:Router,
    private route: ActivatedRoute,
    private clienteService: ClienteService
  ){
    this.route.url.subscribe(segments => {
      this.isSelect = segments.some(segment => segment.path === 'seleccionar')
      if (this.isSelect) this.title = "Selecciona Cliente"
    })
  }

  ngOnInit(): void {
    this.loadListClientes()
  }

  loadListClientes(){
    this.clienteService.findAllMapper().subscribe(clientes => {
      this.clientes = clientes;
    });
  }

  reloadFilterClientes(nameColumn:string){
    this.nameColum = nameColumn
    this.isModified = ! this.isModified
  }

  openFormBoleta(id:number | undefined){
    if( id != undefined) localStorage.setItem('cliente-select-vet',id.toString())
    this.router.navigate(['/menu/boletas/registrar']);
  }

  exportarExcel(): void{
    this.showAlert.question("Deseas descargar Listado de Clientes.xlsx", () => {
      exportToExcel(this.clientes,"Listado de clientes")
    })
  }

  exportarPDF():void{
    exportToPdf("Listado de Clientes",this.clientes,['cliente', 'celular', 'fecha','genero'])
  }

}
