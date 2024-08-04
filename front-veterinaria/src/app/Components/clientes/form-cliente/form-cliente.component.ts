import { NgForm } from '@angular/forms';
import { ShowAlert } from './../../../Functions/ShowAlert';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cliente } from 'src/app/Models/cliente';
import { ClienteService } from 'src/app/Services/cliente.service';
import { FlashMessagesComponent } from 'flash-messages-angular';
import { setImageWithCompressionAndResize } from 'src/app/Functions/FunctionsImage';
import { MascotaService } from 'src/app/Services/mascota.service';
import { MascotaMapper } from 'src/app/Models/mascota';

@Component({
  selector: 'app-form-cliente',
  templateUrl: './form-cliente.component.html',
  styleUrls: ['./form-cliente.component.css']
})
export class FormClienteComponent implements OnInit {

  // Datos de inicializacion
  isEditar: boolean = false
  idCliente: number = 0
  fotoCliente: File | null = null
  title:string = "Registar Cliente"
  cliente: Cliente = { genero:"Masculino" }

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  // Datos de la tabla mascotasCliente
  mascotasCliente: MascotaMapper[] = []
  page = 1

  constructor(
    private route: ActivatedRoute,
    private router:Router,
    private clienteService: ClienteService,
    private mascotaService: MascotaService,
  ) {}

  ngOnInit(): void {
    this.setFormCliente()
  }

  setFormCliente(){
    this.route.params.subscribe(params => {
      this.isEditar = !!params['id']
      if (this.isEditar) {
        this.idCliente = params['id'] as number
        this.setCliente(this.idCliente)
        this.loadMascotasCliente(this.idCliente)
      }
    });
  }

  setCliente(id:number) {
    this.clienteService.findById(id).subscribe(cliente => {
      this.cliente = cliente
      this.title = "Editar Cliente"
    });
  }

  loadMascotasCliente(id:number){
    this.mascotaService.findAllByClienteId(id).subscribe( mascotasCliente => {
      this.mascotasCliente = mascotasCliente
    })
  }

  openFormRegisterMascota(id:number | undefined){
    this.router.navigate(['/menu/mascotas/registrar'], { state: { id } });
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement
    if (input.files) {
      setImageWithCompressionAndResize(input.files).then(({ file, src }) => {
        this.fotoCliente = file
        this.cliente.foto = src
      });
    }
  }

  save(formulario:NgForm, flash:FlashMessagesComponent): void {
    if(! formulario.form.valid){
      flash.show('Llena correctamente el formulario',
      {cssClass: 'alert-danger-config', timeout: 4000})
      return
    }

    if (this.isEditar) {
      this.clienteService.update(this.cliente.id!, this.cliente).subscribe(response => {
        if(this.fotoCliente != null) {
          this.clienteService.setFoto(response.id as number,this.fotoCliente).subscribe(response => {
            this.router.navigate(['/menu/clientes'])
            this.showAlert.save("Cliente Actualizado!")
          })
          this.showAlert.loading()
        } else {
          this.router.navigate(['/menu/clientes'])
          this.showAlert.save("Cliente Actualizado!")
        }
      })
    } else {
      this.clienteService.insert(this.cliente).subscribe(response => {
        if(this.fotoCliente != null) {
          this.clienteService.setFoto(response.id as number,this.fotoCliente).subscribe(response => {
            this.router.navigate(['/menu/clientes'])
            this.showAlert.save("Cliente Registrado!")
          })
          this.showAlert.loading()
      } else {
          this.router.navigate(['/menu/clientes'])
          this.showAlert.save("Cliente Registrado!")
        }
      })
    }
  }

  delete(){
    this.showAlert.delete("¿Seguro que deseas eliminar al cliente?",() => {
      this.clienteService.delete(this.idCliente).subscribe(response => {
        this.router.navigate(['/menu/clientes'])
      })
    },"Cliente Eliminado!","Ningun Cliente Eliminado!")
  }
}
