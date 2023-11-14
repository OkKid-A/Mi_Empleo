import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginViewComponent} from "./session/login-view/login-view.component";
import {SolicitanteViewComponent} from "./solicitante/solicitante-view/solicitante-view.component";
import {SolicitanteBuscadorComponent} from "./solicitante/solicitante-buscador/solicitante-buscador.component";
import {OfertaViewComponent} from "./oferta/oferta-view/oferta-view.component";
import {PostulacionesViewComponent} from "./solicitante/postulaciones-view/postulaciones-view.component";
import {
  SolicitanteEntrevistasViewComponent
} from "./solicitante/solicitante-entrevistas-view/solicitante-entrevistas-view.component";
import {EmpleadorViewComponent} from "./Empleador/empleador-view/empleador-view.component";
import {
  EmpleadorOfertasViewComponent
} from "./Empleador/empleador-ofertas-view/empleador-ofertas-view.component";
import {NuevaOfertaFormComponent} from "./oferta/nueva-oferta-form/nueva-oferta-form.component";
import {EditarOfertaFormComponent} from "./oferta/editar-oferta-form/editar-oferta-form.component";
import {
  EmpleadorPostulacionesViewComponent
} from "./Empleador/empleador-postulaciones-view/empleador-postulaciones-view.component";
import {OfertaPostulacionesViewComponent} from "./oferta/oferta-postulaciones-view/oferta-postulaciones-view.component";
import {EmpEntrevistasViewComponent} from "./Empleador/emp-entrevistas-view/emp-entrevistas-view.component";
import {EntrevistaListComponent} from "./Entrevista/entrevista-list/entrevista-list.component";
import {solicitanteGuard} from "./Guards/solicitante.guard";
import {NotFoundComponent} from "./session/not-found/not-found.component";
import {empleadorGuard} from "./Guards/empleador.guard";
import {AdminViewComponent} from "./admin/admin-view/admin-view.component";
import {adminGuard} from "./Guards/admin.guard";
import {AdminDashboardComponent} from "./admin/admin-dashboar/admin-dashboard.component";
import {CrearCuentaFormComponent} from "./session/crear-cuenta-form/crear-cuenta-form.component";
import {ValidateFormComponent} from "./session/validate-form/validate-form.component";
import {isCompletedGuard} from "./Guards/is-completed.guard";
import {SolicitanteFormComponent} from "./session/solicitante-form/solicitante-form.component";
import {CargadorArchivoComponent} from "./admin/cargador-archivo/cargador-archivo.component";

const routes: Routes = [
  {
    path: '',
    redirectTo:'/login',
    pathMatch: "full"
  },
  {
    path: 'login',
    title: "Login",
    component: LoginViewComponent
  },
  {
    path: 'crear-cuenta',
    title: "Nueva Cuenta",
    component: CrearCuentaFormComponent
  },
  {
    path: 'upload',
    title: "Archivo de Entrada",
    component: CargadorArchivoComponent
  },
  {
    path: 'validate/:codigo',
    title: "Validacion",
    component: ValidateFormComponent
  },
  {
    path: 'admin',
    title: "Inicio",
    component : AdminViewComponent,
    canMatch: [adminGuard],
    children: [
      {path: 'dashboard', component: AdminDashboardComponent}
    ]
  },
  {
    path: 'empleador',
    title: "Inicio",
    component: EmpleadorViewComponent,
    canMatch: [empleadorGuard],
    children:[
      {path: 'inicio',component: EmpleadorOfertasViewComponent},
      {path: 'nueva-oferta',component: NuevaOfertaFormComponent},
      {path: 'nueva-oferta/:codigo', component: NuevaOfertaFormComponent},
      {path: 'postulaciones', component: EmpleadorPostulacionesViewComponent},
      {path: 'postulacion/:codigo',component: OfertaPostulacionesViewComponent},
      {path: 'entrevistas', component: EmpEntrevistasViewComponent},
      {path: 'entrevista/:codigo',component: EntrevistaListComponent}
    ]
  },
  {
    path: 'solicitante',
    title: "Inicio",
    component: SolicitanteViewComponent,
    canMatch: [solicitanteGuard],
    children: [
      {path: 'busqueda',component:SolicitanteBuscadorComponent, title: "Busqueda"},
      {path: 'oferta/:codigo', component: OfertaViewComponent},
      {path: 'solicitudes', component: PostulacionesViewComponent},
      {path: 'entrevistas',component: SolicitanteEntrevistasViewComponent}
    ]
  },
  {
    path: 'solicitante',
    title: "Completar Cuenta",
    canMatch:[solicitanteGuard],
    component: SolicitanteFormComponent
  },
  {
    path: '**',
    title: "No Autorizado",
    component: NotFoundComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
