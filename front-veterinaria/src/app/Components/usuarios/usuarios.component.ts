import { AfterViewChecked, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core'
import { NgForm } from '@angular/forms'
import { Router } from '@angular/router'
import { FlashMessagesComponent } from 'flash-messages-angular'
import { exportToExcel } from 'src/app/Functions/ExportExcel'
import { exportToPdf } from 'src/app/Functions/ExportPdf'
import { ShowAlert } from 'src/app/Functions/ShowAlert'
import { passwordValidator } from 'src/app/Functions/Validator'
import { Rol } from 'src/app/Models/rol'
import { Usuario, UsuarioMapper } from 'src/app/Models/usuario'
import { RolService } from 'src/app/Services/rol.service'
import { UsuarioService } from 'src/app/Services/usuario.service'

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent implements OnInit, AfterViewChecked{

  // Datos de listado
  usuarios: UsuarioMapper[] = []
  roles: Rol[] = []

  // Datos de paginacion
  page = 1

  // Datos de busqueda
  text:string = ''
  nameColum:string = 'username'
  isModified:boolean = false

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  // Datos de registro o edicion
  usuario:Usuario = {roles:[]}
  isEditar:boolean = false
  isEnabled:boolean = false
  titleForm:string = "Registrar Usuario"
  textBottonSave:string = "Guardar registro"

  // Datos del html
  @ViewChild('usuarioForm') usuarioForm: NgForm

  constructor(
    private router:Router,
    private usuarioService: UsuarioService,
    private rolService: RolService,
    private changeDetectorRef: ChangeDetectorRef
  ){

  }

  ngOnInit() {
    this.loadListUsuarios()
    this.loadForaneos()
  }

  ngAfterViewChecked() {
    this.usuarioForm.form.get('password')?.setValidators([passwordValidator()])
    this.usuarioForm.form.get('password')?.updateValueAndValidity()
    this.changeDetectorRef.detectChanges()
  }

  loadListUsuarios(){
    this.usuarioService.findAllMapper().subscribe(usuarios => {
      this.usuarios = usuarios
    })
  }

  loadForaneos(){
    this.rolService.findAll().subscribe(roles => {
      this.roles = roles
    })
  }

  setUsuario(id:number){
    this.usuarioService.findById(id).subscribe(usuario => {
      this.usuario = usuario
      this.isEditar = true
      this.isEnabled = true
      this.titleForm = "Editar Usuario"
      this.textBottonSave = "Actualizar registro"
    })
  }

  cleanUsuario(formulario:NgForm){
    this.usuario = {roles:[]}
    this.isEditar = false
    this.isEnabled = false
    this.titleForm = "Registrar Usuario"
    this.textBottonSave = "Guardar registro"
    formulario.resetForm()
  }

  onEnabledChange(event:any, id:number){
    if (event.target.checked) {
      this.usuarioService.findById(id).subscribe(usuario => {
        this.usuario.password = usuario.password
        this.isEnabled = true
      })
    } else {
      this.isEnabled = false
      this.usuario.password = ""
    }
  }

  isChecked(id: number): boolean {
    return this.usuario.roles!.some(rol => rol.id === id)
  }

  toggleRol(event: Event, id: number) {
    const checked = (event.target as HTMLInputElement).checked
    if (checked) {
      const rol = this.roles.find(rol => rol.id === id)
      if (rol) this.usuario.roles!.push(rol)
    } else {
      const index = this.usuario.roles!.findIndex(rol => rol.id === id)
      if (index !== -1) this.usuario.roles!.splice(index, 1)
    }
  }

  saveUsuario(formulario:NgForm, flash:FlashMessagesComponent){

    if(! formulario.form.valid){
      flash.show('Llena correctamente el formulario',
      {cssClass: 'alert-danger-config', timeout: 4000})
      return
    }

    if(this.usuario.id !== undefined) this.usuarioService.update(this.usuario.id!,this.usuario).subscribe( response => {
      this.showAlert.save("Usuario Actualizado")
      this.loadListUsuarios()
      this.cleanUsuario(formulario)
    }, responseError => {
      this.showAlert.error(responseError.error.message)
    })
    else this.usuarioService.insert(this.usuario).subscribe( response => {
      this.showAlert.save("Usuario Registrado")
      this.loadListUsuarios()
      this.cleanUsuario(formulario)
    }, responseError => {
      this.showAlert.error(responseError.error.message)
    })
  }

  deleteUsuario(id:number){
    this.showAlert.delete("¿Seguro que deseas eliminar esta usuario?",() => {
      this.usuarioService.delete(id).subscribe(response => {
        this.loadListUsuarios()
      })
    },"Usuario Eliminada!","Ninguna Usuario Eliminada!")
  }

  reloadFilterUsuarios(nameColumn:string){
    this.nameColum = nameColumn
    this.isModified = ! this.isModified
  }

  openFormBoleta(id:number | undefined){
    if( id != undefined) localStorage.setItem('usuario-select-vet',id.toString())
    this.router.navigate(['/menu/boletas/registrar'])
  }

  exportarExcel(): void{
    this.showAlert.question("Deseas descargar Listado de Usuarios.xlsx", () => {
      exportToExcel(this.usuarios,"Listado de Usuarios")
    })
  }

  exportarPDF():void{
    exportToPdf("Listado de Usuarios",this.usuarios,['id','username','correo'])
  }
}
