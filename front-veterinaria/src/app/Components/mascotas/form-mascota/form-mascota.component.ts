import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FlashMessagesComponent } from 'flash-messages-angular';
import { setImageWithCompressionAndResize } from 'src/app/Functions/FunctionsImage';
import { ShowAlert } from 'src/app/Functions/ShowAlert';
import { BanioMapper } from 'src/app/Models/banio';
import { CitaMapper } from 'src/app/Models/cita';
import { Cliente } from 'src/app/Models/cliente';
import { Especie } from 'src/app/Models/especie';
import { Mascota } from 'src/app/Models/mascota';
import { Raza } from 'src/app/Models/raza';
import { TratamientoMapper } from 'src/app/Models/tratamiento';
import { AuthService } from 'src/app/Services/auth.service';
import { BanioService } from 'src/app/Services/banio.service';
import { CitaService } from 'src/app/Services/cita.service';
import { ClienteService } from 'src/app/Services/cliente.service';
import { EspecieService } from 'src/app/Services/especie.service';
import { MascotaService } from 'src/app/Services/mascota.service';
import { RazaService } from 'src/app/Services/raza.service';
import { TratamientoService } from 'src/app/Services/tratamiento.service';

@Component({
  selector: 'app-form-mascota',
  templateUrl: './form-mascota.component.html',
  styleUrls: ['./form-mascota.component.css']
})
export class FormMascotaComponent {

  // Datos de inicializacion
  isEditar: boolean = false
  idMascota: number = 0
  cliente_id: number | null = null
  fotoMascota: File | null = null
  title:string = "Registar Mascota"
  mascota: Mascota = {
    nombre: "",
    sexo: "Macho",
    anios: 0,
    meses: 0,
    peso: 0,
    descripcion: "color: \ntamaño: \npelaje: ",
    foto: '',
    eliminado: false,
    raza: {nombre:'', especie:{nombre: ''}},
    cliente: {}
  }

  // Datos de foraneos
  clientes: Cliente[] = []
  razas: Raza[] = []
  especies: Especie[] = []

  // Datos de la tabla tratamientosMascota
  tratamientosMascota: TratamientoMapper[] = []
  pageTratamientos = 1

  // Datos de la tabla baniosMascota
  baniosMascota: BanioMapper[] = []
  pageBanios = 1

  // Datos de la tabla citasMascota
  citasMascota: CitaMapper[] = []
  pageCitas = 1

  // Datos de alerta
  showAlert:ShowAlert = new ShowAlert()

  // Datos de cancelacion y atencion
  idCita:number = 0
  mensaje:string = ""

  // Datos de proteccion
  isRecepcionista:Boolean = false
  isVeterinario:Boolean = false
  isGroomer:Boolean = false

  constructor(
    private route: ActivatedRoute,
    private router:Router,
    private mascotaService: MascotaService,
    private clienteService: ClienteService,
    private razaService: RazaService,
    private especieService: EspecieService,
    private tratamientoService: TratamientoService,
    private banioService: BanioService,
    private citaService: CitaService,
    private authService: AuthService
  ) {
    this.cliente_id = this.router.getCurrentNavigation()?.extras.state?.['id'];
  }

  ngOnInit(): void {
    this.setFormMascota()
    this.loadForeneos()
    this.setRoles()
  }

  setFormMascota(){
    this.route.params.subscribe(params => {
      this.isEditar = !!params['id']
      if (this.isEditar) {
        this.idMascota = params['id'] as number
        this.setMascota(this.idMascota)
        this.loadTratamientosMascota(this.idMascota)
        this.loadBaniosMascota(this.idMascota)
        this.loadCitasMascota(this.idMascota)
      }
    });
  }

  setMascota(id:number) {
    this.mascotaService.findById(id).subscribe(mascota => {
      this.mascota = mascota
      this.title = "Editar Mascota"
    });
  }

  setRoles(){
    this.authService.usuario().subscribe(usuario => {
      if(usuario?.roles != null){
        for(let rol of usuario.roles){
          if(rol.nombre === "RECEPCIONISTA") this.isRecepcionista = true
          if(rol.nombre === "VETERINARIO") this.isVeterinario = true
          if(rol.nombre === "GROOMER") this.isGroomer = true
        }
      }
    })
  }

  loadTratamientosMascota(id:number){
    this.tratamientoService.findAllByMascotaId(id).subscribe( tratamientosMascota => {
      this.tratamientosMascota = tratamientosMascota
    })
  }

  loadBaniosMascota(id:number){
    this.banioService.findAllByMascotaId(id).subscribe( baniosMascota => {
      this.baniosMascota = baniosMascota
    })
  }

  loadCitasMascota(id:number){
    this.citaService.findAllByMascotaId(id).subscribe( citasMascota => {
      this.citasMascota = citasMascota
    })
  }

  openFormRegisterTratamiento(id:number | undefined){
    this.router.navigate(['/menu/tratamientos/registrar'], { state: { id } });
  }

  openFormRegisterBanio(id:number | undefined){
    this.router.navigate(['/menu/banios/registrar'], { state: { id } });
  }

  openFormRegisterCita(id:number | undefined){
    this.router.navigate(['/menu/citas/registrar'], { state: { id } });
  }

  loadForeneos(){

    this.clienteService.findAll().subscribe(clientes => {
      this.clientes = clientes
      if ( this.cliente_id != null) this.clienteService.findById(this.cliente_id).subscribe(cliente => {
        this.mascota.cliente = cliente
      })
      else if (!this.isEditar) this.clienteService.findById(clientes[0].id as number).subscribe(cliente => {
        this.mascota.cliente = cliente
      })
    })

    this.razaService.findAll().subscribe(razas => {
      this.razas = razas
      if (!this.isEditar) this.razaService.findById(razas[0].id as number).subscribe(raza => {
        this.mascota.raza = raza
      })
    })

    this.especieService.findAll().subscribe(especies => {
      this.especies = especies
    })
  }

  refreshRazas(id:number | undefined){
    for (let raza of this.razas) {
      if (raza.especie!.id == id) {
        this.mascota.raza = raza
        break;
      }
    }
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement
    if (input.files) {
      setImageWithCompressionAndResize(input.files).then(({ file, src }) => {
        this.fotoMascota = file
        this.mascota.foto = src
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
      this.mascotaService.update(this.mascota.id!, this.mascota).subscribe(response => {
        if(this.fotoMascota != null) {
          this.mascotaService.setFoto(response.id as number,this.fotoMascota).subscribe(response => {
            this.router.navigate(['/menu/mascotas'])
            this.showAlert.save("Mascota Actualizado!")
          })
          this.showAlert.loading()
        } else {
          this.router.navigate(['/menu/mascotas'])
          this.showAlert.save("Mascota Actualizado!")
        }
      })
    } else {
      this.mascotaService.insert(this.mascota).subscribe(response => {
        if(this.fotoMascota != null) {
          this.mascotaService.setFoto(response.id as number,this.fotoMascota).subscribe(response => {
            this.router.navigate(['/menu/mascotas'])
            this.showAlert.save("Mascota Registrado!")
          })
          this.showAlert.loading()
      } else {
          this.router.navigate(['/menu/mascotas'])
          this.showAlert.save("Mascota Registrado!")
        }
      })
    }
  }

  delete(){
    this.showAlert.delete("¿Seguro que deseas eliminar al mascota?",() => {
      this.mascotaService.delete(this.idMascota).subscribe(response => {
        this.router.navigate(['/menu/mascotas'])
      })
    },"Mascota Eliminada!","Ningun Mascota Eliminada!")
  }

  cancelar(formulario:NgForm, cerrar:HTMLButtonElement){
    if(!formulario.form.valid) return
    this.citaService.cancelar(this.idCita, this.mensaje).subscribe( response => {
      this.showAlert.save(`Cita #${this.idCita} cancelada`)
      this.loadCitasMascota(this.idMascota)
    })
    cerrar.click()
  }

  openFormRegisterTratamientoCita(cita_id:number,cerrar:HTMLButtonElement){
    this.citaService.findById(cita_id).subscribe(cita => {
      const id:number = cita.mascota!.id!
      cerrar.click()
      this.router.navigate(['/menu/tratamientos/registrar'], { state: { id , cita_id } });
    })

  }

  openFormRegisterBanioCita(cita_id:number,cerrar:HTMLButtonElement){
    this.citaService.findById(cita_id).subscribe(cita => {
      const id:number = cita.mascota!.id!
      cerrar.click()
      this.router.navigate(['/menu/banios/registrar'], { state: { id , cita_id } });
    })
  }
}
