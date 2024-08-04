import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
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
import { FormMascotaComponent } from './Components/mascotas/form-mascota/form-mascota.component';
import { FormTratamientoComponent } from './Components/tratamientos/form-tratamiento/form-tratamiento.component';
import { FormBanioComponent } from './Components/banios/form-banio/form-banio.component';
import { LoginComponent } from './Components/login/login.component';
import { AuthGuard } from './Guards/auth.guard';
import { FormProductoComponent } from './Components/productos/form-producto/form-producto.component';
import { FormCitaComponent } from './Components/citas/form-cita/form-cita.component';
import { EspeciesComponent } from './Components/especies/especies.component';
import { RazasComponent } from './Components/razas/razas.component';
import { CategoriasComponent } from './Components/categorias/categorias.component';
import { ProveedoresComponent } from './Components/proveedores/proveedores.component';
import { UsuariosComponent } from './Components/usuarios/usuarios.component';
import { PedidosComponent } from './Components/pedidos/pedidos.component';
import { FormPedidoComponent } from './Components/pedidos/form-pedido/form-pedido.component';

const routes: Routes = [
  {path: '',redirectTo: 'menu',pathMatch: 'full'},
  {path: "login", component: LoginComponent},
  {path: "menu", component: MenuComponent, canActivate:[AuthGuard],
    children: [
      { path: "dashboard", component: DashboardComponent },
      { path: "clientes", component: ClientesComponent },
      { path: "clientes/seleccionar", component: ClientesComponent },
      { path: "clientes/registrar", component: FormClienteComponent },
      { path: "clientes/editar/:id", component: FormClienteComponent },
      { path: "mascotas", component: MascotasComponent },
      { path: "mascotas/registrar", component: FormMascotaComponent },
      { path: "mascotas/editar/:id", component: FormMascotaComponent },
      { path: "productos", component: ProductosComponent },
      { path: "productos/seleccionar", component: ProductosComponent },
      { path: "productos/registrar", component: FormProductoComponent },
      { path: "productos/editar/:id", component: FormProductoComponent },
      { path: "especies", component: EspeciesComponent },
      { path: "razas", component: RazasComponent },
      { path: "categorias", component: CategoriasComponent },
      { path: "proveedores", component: ProveedoresComponent },
      { path: "usuarios", component: UsuariosComponent },
      { path: "boletas", component: BoletasComponent },
      { path: "boletas/registrar", component: FormBoletaComponent },
      { path: "pedidos", component: PedidosComponent },
      { path: "pedidos/registrar", component: FormPedidoComponent },
      { path: "tratamientos", component: TratamientosComponent },
      { path: "tratamientos/registrar", component: FormTratamientoComponent },
      { path: "tratamientos/editar/:id", component: FormTratamientoComponent },
      { path: "banios", component: BaniosComponent },
      { path: "banios/registrar", component: FormBanioComponent },
      { path: "banios/editar/:id", component: FormBanioComponent },
      { path: "citas", component: CitasComponent },
      { path: "citas/registrar", component: FormCitaComponent },
      { path: "citas/editar/:id", component: FormCitaComponent },
      { path: "ingresos", component: IngresosComponent },
      { path: "", redirectTo: "dashboard", pathMatch: "full" }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: false })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
