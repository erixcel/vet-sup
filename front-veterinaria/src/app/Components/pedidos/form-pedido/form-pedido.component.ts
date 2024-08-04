import { Component, OnInit, ViewChild } from '@angular/core'
import { Router } from '@angular/router'
import { forkJoin } from 'rxjs'
import { formatNumber, getDateNow } from 'src/app/Functions/Generales'
import { ShowAlert } from 'src/app/Functions/ShowAlert'
import { ModalTableComponent } from 'src/app/Interfaces/modal-table/modal-table.component'
import { Pedido } from 'src/app/Models/pedido'
import { PedidoProducto } from 'src/app/Models/pedido-producto'
import { Producto } from 'src/app/Models/producto'
import { Proveedor } from 'src/app/Models/proveedor'
import { PedidoService } from 'src/app/Services/pedido.service'
import { ProveedorService } from 'src/app/Services/proveedor.service'

@Component({
  selector: 'app-form-pedido',
  templateUrl: './form-pedido.component.html',
  styleUrls: ['./form-pedido.component.css']
})

export class FormPedidoComponent implements OnInit{

  // Datos del pedido
  pedido:Pedido = {}
  proveedor:Proveedor = {}
  tipo_pago:string = "efectivo"
  detalle_producto:PedidoProducto[] = []
  total:number = 0

  // Datos foraneos
  proveedores:Proveedor[] = []
  productos:Producto[] = []

  //Datos dinamicos
  showButtons: boolean[] = []
  numeroPedido: string = ""
  fechaActual: string = getDateNow()

  // Datos de busqueda
  text:string = ''
  nameColum:string = 'nombre'
  isModified:boolean = false
  isSearch:boolean = false

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  // Referencia tabla modal
  @ViewChild('modalTable') modalTable: ModalTableComponent

  constructor(
    private router:Router,
    private pedidoService:PedidoService,
    private proveedorService:ProveedorService,
  ){

  }

  ngOnInit(): void {
    this.setProveedores()
    this.setNumeroPedido()
  }

  setNumeroPedido() {
    this.pedidoService.findNewId().subscribe( newId => {
      this.numeroPedido = formatNumber(parseInt(newId))
    })
  }

  setProveedores(){
    this.proveedorService.findAll().subscribe(proveedores => {
      this.proveedores = proveedores
      this.proveedor = proveedores[0]
      this.setProductos()
      this.configModalTable()
    })
  }

  setProductos(){
    this.proveedorService.findProductosById(this.proveedor.id!).subscribe( productos => {
      this.productos = productos
    })
  }

  selectProveedor(){
    this.modalTable.open()
  }

  openSearch(){
    this.isSearch = true
  }

  closeSearch(){
    this.isSearch = false
    this.text = ""
  }

  configModalTable(){
    this.modalTable.setTitle("Seleccionar Proveedor")
    this.modalTable.setList(this.proveedores)
    this.modalTable.setIdSelect(this.proveedor.id + "")
    this.modalTable.setListNamesColumns(["Nombre","Celular","Correo"])
    this.modalTable.setListNamesAtributes(["nombre","celular","correo"])
  }

  onModalTableCorrect(){
    if(this.modalTable.getIdSelect() !== this.proveedor.id! + ""){
      if (this.detalle_producto.length > 0) this.showAlert.question("Si cambias de proveedor se reseteara el formulario", () => {
        this.resetProveedores()
      })
      else this.resetProveedores()
    } else this.modalTable.hide()
  }

  resetProveedores(){
    forkJoin({
      proveedor: this.proveedorService.findById(+this.modalTable.getIdSelect()),
      productos: this.proveedorService.findProductosById(+this.modalTable.getIdSelect())
    }).subscribe(({proveedor, productos}) => {
        this.proveedor = proveedor
        this.productos = productos
        this.closeSearch()
        this.modalTable.hide()
    })
    this.detalle_producto = []
    this.total = 0
  }

  isPedidoProducto(idProducto: number): boolean {
    return this.detalle_producto.some(detalle => detalle.producto!.id === idProducto)
  }

  addPedidoProducto(producto: Producto, cantidad:number) {
    const pedidoProducto:PedidoProducto = {
      producto: producto,
      precio_compra: producto.precio_compra,
      cantidad: cantidad,
      importe: cantidad * producto.precio_compra!
    }
    this.detalle_producto.push(pedidoProducto)
    this.setTotal()
  }

  removePedidoProducto(idProducto:number) {
    this.detalle_producto = this.detalle_producto.filter(detalle => detalle.producto!.id !== idProducto)
    this.setTotal()
  }

  updateDetalle(idProducto: number, valor: number) {
    this.detalle_producto.forEach(detalle => {
      if(detalle.producto!.id === idProducto) {
        detalle.cantidad = detalle.cantidad! + valor === 0 ? 1 : detalle.cantidad! + valor
        detalle.importe = detalle.cantidad! * detalle.precio_compra!
      }
    })
    this.setTotal()
  }

  changeCantidad(input: HTMLInputElement,idProducto:number) {
    if(input.value === "") input.value = "1"
    if(parseInt(input.value) < 1) input.value = "1"
    this.detalle_producto.forEach(detalle => {
      if(detalle.producto!.id === idProducto) {
        detalle.cantidad = parseInt(input.value)
        detalle.importe = detalle.cantidad! * detalle.precio_compra!
      }
    })
    this.setTotal()
  }

  setTotal() {
    this.total = 0
    this.detalle_producto.forEach(detalle => {
      this.total += detalle.importe!
    })
  }

  validInput(input: HTMLInputElement) {
    if(input.value === "") input.value = "1"
    else if(parseInt(input.value) < 1) input.value = "1"
  }

  updateTipo(text: string, element:HTMLElement) {
    if (text === "EFECTIVO"){
      this.tipo_pago = "efectivo"
      element.textContent = text;
    }
    else if (text === "YAPE"){
      this.tipo_pago = "yape"
      element.textContent = text;
    }
    else if (text === "TARJETA"){
      this.tipo_pago = "tarjeta"
      element.textContent = text;
    }
  }

  cleanDetalleProducto(){
    this.detalle_producto = []
  }

  registerPedido(){
    if(this.detalle_producto.length == 0) {
      this.showAlert.error("No hay productos en la orden")
      return
    }

    const pedido:Pedido = {
      proveedor: this.proveedor,
      detalle_producto: this.detalle_producto,
      tipo_pago: this.tipo_pago,
      total: this.total
    }

    this.pedidoService.insert(pedido).subscribe( response => {
      this.showAlert.save("Orden realizada correctamente!")
      this.pedido = {}
      this.tipo_pago = "efectivo"
      this.detalle_producto = []
      this.total = 0
      this.setProveedores()
      this.router.navigate(['/menu/pedidos'])
    })
  }

}
