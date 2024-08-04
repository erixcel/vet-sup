import { Component, OnInit } from '@angular/core'
import { Router } from '@angular/router'
import { forkJoin } from 'rxjs'
import { ShowAlert } from 'src/app/Functions/ShowAlert'
import { Boleta } from 'src/app/Models/boleta'
import { Cliente } from 'src/app/Models/cliente'
import { BoletaProducto } from 'src/app/Models/boleta-producto'
import { Producto } from 'src/app/Models/producto'
import { BoletaService } from 'src/app/Services/boleta.service'
import { ClienteService } from 'src/app/Services/cliente.service'
import { ProductoService } from 'src/app/Services/producto.service'
import { formatNumber, getDateNow } from 'src/app/Functions/Generales'


@Component({
  selector: 'app-form-boleta',
  templateUrl: './form-boleta.component.html',
  styleUrls: ['./form-boleta.component.css']
})


export class FormBoletaComponent implements OnInit{

  // Datos para registro de boleta
  productosSelectValues: { id: number; cantidad: number }[] = []
  productosSelect: Producto[] = []
  clienteSelect: Cliente = {}
  detalle_producto: BoletaProducto[] = []
  sub_total: number = 0
  igv: number = 0
  precio_final: number = 0
  tipo_pago: string = "efectivo"
  saldo: number = 0
  vuelto: number = 0

  //Datos dinamicos
  numeroBoleta: string = ""
  fechaActual: string = getDateNow()

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  constructor(
    private router:Router,
    private clienteService: ClienteService,
    private productoService: ProductoService,
    private boletaService: BoletaService
  ) {

  }

  ngOnInit() {
    this.setProductosSelectValues()
    this.setClienteSelect()
    this.setProductosSelect()
    this.setNumeroBoleta()
  }

  setNumeroBoleta() {
    this.boletaService.findNewId().subscribe( newId => {
      this.numeroBoleta = formatNumber(parseInt(newId))
    })
  }

  setProductosSelectValues() {
    const storedValue = localStorage.getItem("productos-select-vet")
    this.productosSelectValues = storedValue ? JSON.parse(storedValue) : []
  }

  setClienteSelect() {
    const storedValue = localStorage.getItem('cliente-select-vet')
    const id: number = storedValue ? parseInt(storedValue) : -1
    if (id != -1) {
      this.clienteService.findById(id).subscribe((cliente) => {
        this.clienteSelect = cliente
      })
    } else {
      this.clienteService.findAll().subscribe(clientes => {
        this.clienteSelect = clientes[0]
    })}
  }

  setProductosSelect() {
    const requests = this.productosSelectValues.map((producto) =>
      this.productoService.findById(producto.id)
    )
    forkJoin(requests).subscribe((productos) => {
      this.productosSelect = productos.filter((producto) => producto != null)
      this.setDetalleProducto()
    })
  }

  setDetalleProducto() {
    this.detalle_producto = this.productosSelect.map((producto) => {
      const productoData = this.productosSelectValues.find((p) => p.id === producto.id)
      return {
        producto: producto,
        precio: producto!.precio_venta,
        cantidad: productoData ? productoData.cantidad : 0,
        total: producto!.precio_venta !== undefined && productoData ? producto!.precio_venta * productoData.cantidad: 0,
      }
    })
    this.setPreciosDetalle()
  }

  setPreciosDetalle(){
    this.sub_total = 0
    for(let detalle of this.detalle_producto){
      this.sub_total += detalle.total!
    }
    this.sub_total =  parseFloat(this.sub_total.toFixed(2))
    this.igv = parseFloat((this.sub_total * 0.18).toFixed(2))
    this.precio_final = parseFloat((this.sub_total + this.igv).toFixed(2))
    this.saldo = this.precio_final

    if(this.tipo_pago !== "efectivo") {
      this.saldo = this.precio_final
      this.vuelto = 0
    }
  }

  onSaldoChange(event: Event) {
    const newValue = parseFloat((event.target as HTMLInputElement).value).toFixed(2);
    this.saldo = parseFloat(newValue)
    this.vuelto = parseFloat((this.saldo - this.precio_final).toFixed(2))
  }


  updateDetalle(idProducto: number | undefined, valor: number) {

    let detalle = this.detalle_producto.find(
      (detalle) => detalle.producto!.id === idProducto
    )
    let producto = this.productosSelect.find(
      (producto) => producto.id === idProducto
    )
    let productoSelect = this.productosSelectValues.find(
      (producto) => producto.id === idProducto
    )

    if (detalle && producto && productoSelect) {
      const newCantidad = detalle.cantidad! + valor
      if (producto.stock! >= newCantidad && 1 <= newCantidad) {
        detalle.cantidad = newCantidad
        detalle.total = detalle.precio! * detalle.cantidad!
        productoSelect.cantidad = newCantidad
        this.saveProductosSelectValues()
        this.setPreciosDetalle()
      }
    }
  }

  removeProducto(idProducto: number | undefined) {
    this.productosSelect = this.productosSelect.filter(
      (producto) => producto!.id !== idProducto
    )
    this.productosSelectValues = this.productosSelectValues.filter(
      (producto) => producto.id !== idProducto
    )
    this.setDetalleProducto()
    this.saveProductosSelectValues()
  }

  moveProducto(idProducto: number | undefined, direction: 0 | 1) {
    const index = this.productosSelect.findIndex((producto) => producto!.id === idProducto)
    if (index === -1) return

    if (direction === 1 && index < this.productosSelect.length - 1) {
      [
        this.productosSelect[index],
        this.productosSelect[index + 1],
      ] = [this.productosSelect[index + 1], this.productosSelect[index]]
    } else if (direction === 0 && index > 0) {
      [
        this.productosSelect[index],
        this.productosSelect[index - 1],
      ] = [this.productosSelect[index - 1], this.productosSelect[index]]
    }
    this.setDetalleProducto()
    this.updateProductosSelectValues()
  }

  updateProductosSelectValues() {
    this.productosSelectValues = this.productosSelect.map((producto) => {
      const productoSeleccionado = this.productosSelectValues.find(
        (p) => p.id === producto.id
      );
      return {
        id: producto.id!,
        cantidad: productoSeleccionado ? productoSeleccionado.cantidad : 0,
      };
    });
    this.saveProductosSelectValues();
  }

  saveProductosSelectValues() {
    localStorage.setItem('productos-select-vet',JSON.stringify(this.productosSelectValues))
  }

  updateTipo(text: string, element:HTMLElement) {
    if (text === "EFECTIVO"){
      this.tipo_pago = "efectivo"
      element.textContent = text;
    }
    else if (text === "YAPE"){
      this.tipo_pago = "yape"
      this.saldo = this.precio_final
      this.vuelto = 0
      element.textContent = text;
    }
    else if (text === "TARJETA"){
      this.tipo_pago = "tarjeta"
      this.saldo = this.precio_final
      this.vuelto = 0
      element.textContent = text;
    }
  }

  registerBoleta(){

    if(this.detalle_producto.length == 0) {
      this.showAlert.error("No hay productos a vender")
      return
    }

    const boleta:Boleta = {
      cliente: this.clienteSelect,
      detalle_producto: this.detalle_producto,
      tipo_pago: this.tipo_pago,
      vuelto: this.vuelto,
      sub_total: this.sub_total,
      igv: this.igv,
      precio_final: this.precio_final
    }

    this.boletaService.insert(boleta).subscribe( response => {
      this.showAlert.save("Venta realizada correctamente!")
      this.deleteLocalStorage()
      this.setProductosSelectValues()
      this.productosSelect = []
      this.detalle_producto = []
      this.router.navigate(['/menu/boletas'])
    })

  }

  cleanDetalleProducto() {
    this.deleteLocalStorage()
    this.setProductosSelectValues()
    this.productosSelect = []
    this.detalle_producto = []
  }

  deleteLocalStorage(){
    localStorage.removeItem("cliente-select-vet");
    localStorage.removeItem("productos-select-vet");
  }

}
