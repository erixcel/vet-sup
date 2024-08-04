import { Component, OnInit } from '@angular/core'
import { NgForm } from '@angular/forms'
import { ActivatedRoute, Router } from '@angular/router'
import { FlashMessagesComponent } from 'flash-messages-angular'
import { setImageWithCompressionAndResize } from 'src/app/Functions/FunctionsImage'
import { ShowAlert } from 'src/app/Functions/ShowAlert'
import { Categoria } from 'src/app/Models/categoria'
import { Producto } from 'src/app/Models/producto'
import { CategoriaService } from 'src/app/Services/categoria.service'
import { ProductoService } from 'src/app/Services/producto.service'

import * as $ from 'jquery'
import { Proveedor } from 'src/app/Models/proveedor'
import { ProveedorService } from 'src/app/Services/proveedor.service'

@Component({
  selector: 'app-form-producto',
  templateUrl: './form-producto.component.html',
  styleUrls: ['./form-producto.component.css']
})
export class FormProductoComponent implements OnInit{

  // Datos de inicializacion
  isEditar: boolean = false
  idProducto: number = 0
  fotoProducto: File | null = null
  title:string = "Registar Producto"
  producto: Producto = {
    foto:"",
    marca:"",
    stock: 0,
    precio_venta: 0,
    precio_compra: 0,
    unidad_medida: "1",
    categoria:{nombre:''},
    proveedores:[]
  }

  // Datos de foraneos
  categorias: Categoria[] = []
  proveedores: Proveedor[] = []

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  // Datos de manipulacion
  cantidad: number = 1

  constructor(
    private route: ActivatedRoute,
    private router:Router,
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private proveedorService: ProveedorService,
  ) {

  }

  ngOnInit(): void {
    this.setFormProducto()
    this.loadForeneos()
    this.redimensionarImagen()
  }

  // ngAfterViewChecked() {
  //   if (this.producto.unidad_medida === '') {
  //     this.cantidad = 0;
  //   }
  // }

  setFormProducto(){
    this.route.params.subscribe(params => {
      this.isEditar = !!params['id']
      if (this.isEditar) {
        this.idProducto = params['id'] as number
        this.setProducto(this.idProducto)
      }
    })
  }

  setProducto(id:number) {
    this.productoService.findById(id).subscribe(producto => {
      this.producto = producto
      this.title = "Editar Producto"
    })
  }

  loadForeneos(){
    this.categoriaService.findAll().subscribe(categorias => {
      this.categorias = categorias
      if (!this.isEditar) this.producto.categoria = categorias[0]
    })
    this.proveedorService.findAll().subscribe(proveedores => {
      this.proveedores = proveedores
      if (!this.isEditar) this.producto.proveedores = [proveedores[0]]
    })
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement
    if (input.files) {
      setImageWithCompressionAndResize(input.files).then(({ file, src }) => {
        this.fotoProducto = file
        this.producto.foto = src
      })
    }
  }

  // Metodo que permite hacer responsiva la imagen
  redimensionarImagen() {

    const marcoImagen = document.querySelector('#marcoImagen') as Element

    const estado = () => {
      const altoMaximo = $("#marcoImagen").height()
      const borde = (parseInt($("#marcoImagen").css("border-width")) * 2)
      const anchoMaximo = $("#marcoImagen").width()
      if (window.matchMedia('(max-width: 575.5px)').matches) {
        $('#marcoImagen').css('height', anchoMaximo as number + borde)
        $('#imgProducto').css('max-height', anchoMaximo as number)
      } else {
        $('#marcoImagen').css('height', 'auto')
        $('#imgProducto').css('max-height', altoMaximo as number)
      }
    }
    new ResizeObserver(estado).observe(marcoImagen)
  }

  isChecked(id: number): boolean {
    return this.producto.proveedores!.some(proveedor => proveedor.id === id)
  }

  toggleProveedor(event: Event, id: number) {
    const checked = (event.target as HTMLInputElement).checked
    if (checked) {
      const proveedor = this.proveedores.find(proveedor => proveedor.id === id)
      if (proveedor) this.producto.proveedores!.push(proveedor)
    } else {
      const index = this.producto.proveedores!.findIndex(proveedor => proveedor.id === id)
      if (index !== -1) this.producto.proveedores!.splice(index, 1)
    }
  }

  setUnidadMedida(option: string) {
    this.producto.unidad_medida = this.cantidad + ' ' + option;
  }

  resetUnidadMedida() {
    this.producto.unidad_medida = ""
  }

  isNumberUnidadMedida() {
    return /^\d*\.?\d*$/.test(this.producto.unidad_medida!)
  }

  save(formulario:NgForm, flash:FlashMessagesComponent): void {
    if(! formulario.form.valid){
      flash.show('Llena correctamente el formulario',
      {cssClass: 'alert-danger-config', timeout: 4000})
      return
    }

    if (this.isEditar) {
      this.productoService.update(this.producto.id!, this.producto).subscribe(response => {
        if(this.fotoProducto != null) {
          this.productoService.setFoto(response.id as number,this.fotoProducto).subscribe(response => {
            this.router.navigate(['/menu/productos'])
            this.showAlert.save("Producto Actualizado!")
          })
          this.showAlert.loading()
        } else {
          this.router.navigate(['/menu/productos'])
          this.showAlert.save("Producto Actualizado!")
        }
      })
    } else {
      this.productoService.insert(this.producto).subscribe(response => {
        if(this.fotoProducto != null) {
          this.productoService.setFoto(response.id as number,this.fotoProducto).subscribe(response => {
            this.router.navigate(['/menu/productos'])
            this.showAlert.save("Producto Registrado!")
          })
          this.showAlert.loading()
      } else {
          this.router.navigate(['/menu/productos'])
          this.showAlert.save("Producto Registrado!")
        }
      })
    }
  }

  delete(){
    this.showAlert.delete("¿Seguro que deseas eliminar el producto?",() => {
      this.productoService.delete(this.idProducto).subscribe(response => {
        this.router.navigate(['/menu/productos'])
      })
    },"Producto Eliminado!","Ningun Producto Eliminado!")
  }

}
