import { Component, OnInit } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { exportToExcel } from 'src/app/Functions/ExportExcel'
import { exportToPdf } from 'src/app/Functions/ExportPdf'
import { ShowAlert } from 'src/app/Functions/ShowAlert'
import { ProductoMapper } from 'src/app/Models/producto'
import { ProductoService } from 'src/app/Services/producto.service'

@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css']
})
export class ProductosComponent implements OnInit{

  // Datos de listado
  productos: ProductoMapper[] = []
  productosSeleccionados: { id: number; cantidad: number }[] = []
  productosGirados: number[] = []

  // Datos de paginacion
  page = 1

  // Datos de busqueda
  text:string = ''
  nameColum:string = 'nombre'
  isModified:boolean = false

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  // Datos de seleccion
  isSelect:Boolean = false
  title:string = "Panel de Productos"

  constructor(
    private router:Router,
    private route: ActivatedRoute,
    private productoService: ProductoService
  ){
    this.route.url.subscribe(segments => {
      this.isSelect = segments.some(segment => segment.path === 'seleccionar');
      if (this.isSelect) this.title = "Selecciona Productos"
    });

  }

  ngOnInit(): void {
    this.loadListProductos()
    this.setProductosSelect()
  }

  loadListProductos(){
    this.productoService.findAllMapper().subscribe(productos => {
      this.productos = productos
    })
  }

  reloadFilterProductos(nameColumn:string){
    this.nameColum = nameColumn
    this.isModified = ! this.isModified
  }

  pushButtonShop(idProducto: number, stock: number): void {
    if (this.isProductoSelected(idProducto)) {
      this.deselectProducto(idProducto);
    } else {
      this.selectProducto(idProducto, stock);
    }
    this.saveLocalStorage();
  }

  deselectProducto(idProducto: number): void {
    this.productosSeleccionados = this.productosSeleccionados.filter(
      (producto) => producto.id !== idProducto
    )
  }

  selectProducto(idProducto: number, stock: number): void {
    if (stock <= 0) {
      this.showAlert.error("El producto sin stock")
    } else {
      this.productosSeleccionados.push({ id: idProducto, cantidad: 1 })
    }
  }

  isProductoSelected(idProducto: number): boolean {
    return this.productosSeleccionados.some((producto) => producto.id === idProducto)
  }

  pushInformation(idProducto: number): void {
    if (this.productosGirados.includes(idProducto)) {
      this.rotateFront(idProducto)
    } else {
      this.rotateBack(idProducto)
    }
  }

  rotateFront(idProducto: number): void {
    this.productosGirados = this.productosGirados.filter(id => id != idProducto)
  }

  rotateBack(idProducto: number): void {
    this.productosGirados.push(idProducto)
  }

  setProductosSelect() {
    try {
      const productosGuardados = localStorage.getItem("productos-select-vet")
      if (productosGuardados) this.productosSeleccionados = JSON.parse(productosGuardados)
    } catch (error) {
      this.clearLocalStorage()
    }
  }

  saveLocalStorage() {
    localStorage.setItem("productos-select-vet",JSON.stringify(this.productosSeleccionados))
  }

  clearLocalStorage() {
    this.productosSeleccionados = []
    this.saveLocalStorage()
  }

  exportarExcel(): void{
    this.showAlert.question("Deseas descargar Listado de Productos.xlsx", () => {
      exportToExcel(this.productos,"Listado de productos")
    })
  }

  exportarPDF():void{
    exportToPdf("Listado de Productos",this.productos,['nombre','categoria','tipo', 'stock','precio'])
  }
}
