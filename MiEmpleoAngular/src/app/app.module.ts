import {NgModule, RendererFactory2} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import {AppRoutingModule} from "./app-routing.module";
import { AppComponent } from './app.component';
import { LoginViewComponent } from './session/login-view/login-view.component';
import { LoginFormComponent } from './session/login-form/login-form.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {AngularMaterialModule} from "./angular-material.module";
import {SolicitanteViewComponent} from "./solicitante/solicitante-view/solicitante-view.component";
import { SolicitanteHeaderComponent } from './headers/solicitante-header/solicitante-header.component';
import { SolicitanteBuscadorComponent } from './solicitante/solicitante-buscador/solicitante-buscador.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { OfertaCardComponent } from './oferta/oferta-card/oferta-card.component';
import { OfertaListComponent } from './oferta/oferta-list/oferta-list.component';
import { OfertaViewComponent } from './oferta/oferta-view/oferta-view.component';
import { PostulacionesViewComponent } from './solicitante/postulaciones-view/postulaciones-view.component';
import { PostulacionesListComponent } from './solicitante/postulaciones-list/postulaciones-list.component';
import { PostulacionCardComponent } from './solicitante/postulacion-card/postulacion-card.component';
import { SolicitanteEntrevistasViewComponent } from './solicitante/solicitante-entrevistas-view/solicitante-entrevistas-view.component';
import { EmpladorDetailComponent } from './solicitante/emplador-detail/emplador-detail.component';
import { EmpleadorViewComponent } from './Empleador/empleador-view/empleador-view.component';
import { EmpleadorHeaderComponent } from './headers/empleador-header/empleador-header.component';
import { EmpleadorOfertasViewComponent } from './Empleador/empleador-ofertas-view/empleador-ofertas-view.component';
import { NuevaOfertaFormComponent } from './oferta/nueva-oferta-form/nueva-oferta-form.component';
import { CategoriaSelectorComponent } from './oferta/nueva-oferta-form/categoria-selector/categoria-selector.component';
import { EditarOfertaFormComponent } from './oferta/editar-oferta-form/editar-oferta-form.component';
import {ModalModule} from "ngx-bootstrap/modal";
import { EmpleadorPostulacionesViewComponent } from './Empleador/empleador-postulaciones-view/empleador-postulaciones-view.component';
import { OfertaPostulacionesViewComponent } from './oferta/oferta-postulaciones-view/oferta-postulaciones-view.component';
import { CandidatoCardComponent } from './Empleador/candidato-card/candidato-card.component';
import { EmpEntrevistasViewComponent } from './Empleador/emp-entrevistas-view/emp-entrevistas-view.component';
import { EntrevistaListComponent } from './Entrevista/entrevista-list/entrevista-list.component';
import { EntrevistaCardComponent } from './Entrevista/entrevista-card/entrevista-card.component';
import { EntrevistasTableComponent } from './Entrevista/entrevistas-table/entrevistas-table.component';
import { NotFoundComponent } from './session/not-found/not-found.component';
import { AdminViewComponent } from './admin/admin-view/admin-view.component';
import { AdminHeaderComponent } from './headers/admin-header/admin-header.component';
import { AdminDashboardComponent } from './admin/admin-dashboar/admin-dashboard.component';
import { CrearCuentaFormComponent } from './session/crear-cuenta-form/crear-cuenta-form.component';
import { ValidateFormComponent } from './session/validate-form/validate-form.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginViewComponent,
    LoginFormComponent,
    SolicitanteViewComponent,
    SolicitanteHeaderComponent,
    SolicitanteBuscadorComponent,
    OfertaCardComponent,
    OfertaListComponent,
    OfertaViewComponent,
    PostulacionesViewComponent,
    PostulacionesListComponent,
    PostulacionCardComponent,
    SolicitanteEntrevistasViewComponent,
    EmpladorDetailComponent,
    EmpleadorViewComponent,
    EmpleadorHeaderComponent,
    EmpleadorOfertasViewComponent,
    NuevaOfertaFormComponent,
    CategoriaSelectorComponent,
    EditarOfertaFormComponent,
    EmpleadorPostulacionesViewComponent,
    OfertaPostulacionesViewComponent,
    CandidatoCardComponent,
    EmpEntrevistasViewComponent,
    EntrevistaListComponent,
    EntrevistaCardComponent,
    EntrevistasTableComponent,
    NotFoundComponent,
    AdminViewComponent,
    AdminHeaderComponent,
    AdminDashboardComponent,
    CrearCuentaFormComponent,
    ValidateFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    AngularMaterialModule,
    FontAwesomeModule,
      ModalModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
