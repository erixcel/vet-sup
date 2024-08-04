import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { FlashMessagesModule } from 'flash-messages-angular';
import { NgxPaginationModule } from 'ngx-pagination';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { CargarJS } from './Functions/CargarJS';
import { CargarCSS } from './Functions/CargarCSS';

import { AppComponent } from './app.component';
import { MenuComponent } from './Components/menu/menu.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { ClientesComponent } from './Components/clientes/clientes.component';
import { MascotasComponent } from './Components/mascotas/mascotas.component';
import { CitasComponent } from './Components/citas/citas.component';
import { FormBoletaComponent } from './Components/boletas/form-boleta/form-boleta.component';
import { BoletasComponent } from './Components/boletas/boletas.component';
import { TratamientosComponent } from './Components/tratamientos/tratamientos.component';
import { BaniosComponent } from './Components/banios/banios.component';
import { IngresosComponent } from './Components/ingresos/ingresos.component';
import { ProductosComponent } from './Components/productos/productos.component';
import { FormClienteComponent } from './Components/clientes/form-cliente/form-cliente.component';
import { FilterTablePipe } from './Pipes/filter-table.pipe';
import { FilterTableModalPipe } from './Pipes/filter-table-modal.pipe';
import { FormMascotaComponent } from './Components/mascotas/form-mascota/form-mascota.component';
import { FormTratamientoComponent } from './Components/tratamientos/form-tratamiento/form-tratamiento.component';
import { FormBanioComponent } from './Components/banios/form-banio/form-banio.component';
import { LoginComponent } from './Components/login/login.component';
import { JwtInterceptor } from './Interceptors/jwt-interceptor';
import { FormProductoComponent } from './Components/productos/form-producto/form-producto.component';
import { FormCitaComponent } from './Components/citas/form-cita/form-cita.component';
import { ModalTableComponent } from './Interfaces/modal-table/modal-table.component';
import { ModalInfoComponent } from './Interfaces/modal-info/modal-info.component';
import { EspeciesComponent } from './Components/especies/especies.component';
import { RazasComponent } from './Components/razas/razas.component';
import { CategoriasComponent } from './Components/categorias/categorias.component';
import { ProveedoresComponent } from './Components/proveedores/proveedores.component';
import { UsuariosComponent } from './Components/usuarios/usuarios.component';
import { PedidosComponent } from './Components/pedidos/pedidos.component';
import { FormPedidoComponent } from './Components/pedidos/form-pedido/form-pedido.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    DashboardComponent,
    ClientesComponent,
    MascotasComponent,
    CitasComponent,
    FormBoletaComponent,
    BoletasComponent,
    TratamientosComponent,
    BaniosComponent,
    IngresosComponent,
    ProductosComponent,
    FilterTablePipe,
    FilterTableModalPipe,
    FormClienteComponent,
    FormMascotaComponent,
    FormTratamientoComponent,
    FormBanioComponent,
    LoginComponent,
    FormProductoComponent,
    FormCitaComponent,
    ModalTableComponent,
    ModalInfoComponent,
    EspeciesComponent,
    RazasComponent,
    CategoriasComponent,
    ProveedoresComponent,
    UsuariosComponent,
    PedidosComponent,
    FormPedidoComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    FlashMessagesModule.forRoot(),
    NgxPaginationModule
  ],
  providers: [
    CargarJS,
    CargarCSS,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
