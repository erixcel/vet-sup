import { Component, EventEmitter, OnInit, Output } from '@angular/core'
import { Modal } from 'bootstrap'
// import { backdropAnimation, modalAnimation } from 'src/app/Animations/animations'



@Component({
  selector: 'app-modal-table',
  templateUrl: './modal-table.component.html',
  styleUrls: ['./modal-table.component.css'],
  // animations: [backdropAnimation, modalAnimation]
})

export class ModalTableComponent implements OnInit {

  // Datos de Emision
  @Output() onClose = new EventEmitter<void>()
  @Output() onCorrect = new EventEmitter<void>()

  // Datos de inicializacion
  idSelect: string
  title: string

  // Lista de Listado
  list: any[]
  listNamesColumns: string[]
  listNamesAtributes: string[]


  // Datos de paginacion
  page = 1

  // Datos de busqueda
  text:string = ''
  nameColum:string = 'cliente'
  isModified:boolean = false

  // Crear una nueva instancia del modal
  modal: Modal

  constructor(){
  }

  ngOnInit(): void {
    this.modal = new Modal(document.getElementById('tablaModal') as HTMLElement)
  }

  open() {
    this.modal.show()
  }

  close() {
    this.modal.hide()
    this.onClose.emit()
  }

  hide() {
    this.modal.hide()
  }

  correct() {
    this.onCorrect.emit()
  }

  returnValue(item:any,atributo:string){
    return eval(`item.${atributo}`)
  }

  getIdSelect(){
    return this.idSelect
  }

  setIdSelect(value: string) {
    this.idSelect = value
  }

  setTitle(value: string) {
    this.title = value
  }

  setList(value: any[]) {
    this.list = value
  }
  setListNamesColumns(value: string[]) {
    this.listNamesColumns = value
  }
  setListNamesAtributes(value: string[]) {
    this.listNamesAtributes = value
  }

}
