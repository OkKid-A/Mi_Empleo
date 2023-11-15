import {Component, OnInit, TemplateRef} from '@angular/core';
import {Entrevista} from "../../../entities/entrevista";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {EntrevistaService} from "../../../services/ofertas/entrevista.service";
import {LocalStorageVariables} from "../../../share/local-storage-variables";
import {OfertaService} from "../../../services/ofertas/oferta.service";
import {Oferta} from "../../../entities/oferta";

@Component({
  selector: 'app-solicitante-entrevistas-view',
  templateUrl: './solicitante-entrevistas-view.component.html',
  styleUrls: ['./solicitante-entrevistas-view.component.css']
})
export class SolicitanteEntrevistasViewComponent implements OnInit{

  entrevistas : Entrevista[] = [];
  oferta! : Oferta
  modalRef! : BsModalRef;
  constructor(private modalService: BsModalService,
              private entrevistasServices : EntrevistaService,
              private ofertaService: OfertaService) {
  }

  ngOnInit(): void {
    this.entrevistasServices.getEntrevistasUsuario(localStorage.getItem(LocalStorageVariables.LOCAL_USER)??"").subscribe({
      next:(entrevistas)=>{
        this.entrevistas = entrevistas;
      }
    })
  }

  seleccionarEntrevista(entrevista: Entrevista, template: TemplateRef<any>){
    this.ofertaService.getOfertaById(String(entrevista.codigoOferta)).subscribe((oferta : Oferta) => {
      this.oferta = oferta;
      this.openModal(template)
    })
  }

  openModal(template : TemplateRef<any>){
    this.modalRef = this.modalService.show(template)
  }
}
