import { Component, OnInit } from '@angular/core'
import { getCurrentDate } from 'src/app/Functions/Generales'
import { IngresoBody } from 'src/app/Models/ingreso'
import { BanioService } from 'src/app/Services/banio.service'
import { BoletaService } from 'src/app/Services/boleta.service'
import { IngresoService } from 'src/app/Services/ingreso.service'
import { TratamientoService } from 'src/app/Services/tratamiento.service'

@Component({
  selector: 'app-ingresos',
  templateUrl: './ingresos.component.html',
  styleUrls: ['./ingresos.component.css']
})
export class IngresosComponent implements OnInit{

  ingresos: IngresoBody[] = []
  sumaIngresos: number = 0
  ingresoBanios: number = 0
  ingresoTratamientos: number = 0
  ingresoVentas: number = 0

  ingresosDropeados: {[key: string]: any} = {}
  today = new Date().toLocaleDateString('es-PE', {year: 'numeric', month: '2-digit', day: '2-digit'}).split('/').reverse().join('-')

  constructor(
    private ingresoService: IngresoService,
    private banioService: BanioService,
    private tratamientoService: TratamientoService,
    private boletaService: BoletaService,
  ) {}

  ngOnInit() {
    console.log(this.today)
    this.setIngresos()
    this.setSumaIngresos()
  }

  setIngresos(){
    this.ingresoService.findAllByFecha(this.today).subscribe(data => {
      this.ingresos = data
      this.ingresoBanios = 0
      this.ingresoTratamientos = 0
      this.ingresoVentas = 0
      if (data !== null) data.forEach(ingreso => {
        if(ingreso.servicio === "Baño") this.ingresoBanios += ingreso.precio
        if(ingreso.servicio === "Tratamiento") this.ingresoTratamientos += ingreso.precio
        if(ingreso.servicio === "Venta") this.ingresoVentas += ingreso.precio
      })
      this.ingresoBanios = +this.ingresoBanios.toFixed(2)
      this.ingresoTratamientos = +this.ingresoTratamientos.toFixed(2)
      this.ingresoVentas = +this.ingresoVentas.toFixed(2)
    })
  }

  setSumaIngresos(){
    this.ingresoService.sumTotalByFecha(this.today).subscribe(response => {
      this.sumaIngresos = response.total !== null ? response.total : 0
    })
  }

  showDetails(id: number, servicio: string) {
    if(servicio === "Baño") {
      this.banioService.findById(id).subscribe(banio => {
        this.ingresosDropeados["Baño-"+id] = banio
      })
    } else if(servicio === "Tratamiento") {
      this.tratamientoService.findById(id).subscribe(tratamiento => {
        this.ingresosDropeados["Tratamiento-"+id] = tratamiento
      })
    } else if(servicio === "Venta") {
      this.boletaService.findById(id).subscribe(boleta => {
        this.ingresosDropeados["Venta-"+id] = boleta
      })
    }
  }

  hideDetails(id: number, servicio: string) {
    delete this.ingresosDropeados[servicio+"-"+id]
  }

  isDropeado(id: number, servicio: string) {
    return (servicio+"-"+id) in this.ingresosDropeados
  }

  getDropeado(id: number, servicio: string) {
    return this.ingresosDropeados[servicio+"-"+id]
  }

  increment(numero: number){
    const fecha = new Date(this.today)
    fecha.setDate(fecha.getDate() + numero)
    this.today = fecha.toISOString().split('T')[0]
    this.changeFecha()
  }

  changeFecha(){
    this.setIngresos()
    this.setSumaIngresos()
  }

}
