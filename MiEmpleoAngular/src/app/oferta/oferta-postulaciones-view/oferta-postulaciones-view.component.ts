import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {OfertaService} from "../../../services/ofertas/oferta.service";
import {SolicitudService} from "../../../services/ofertas/solicitud.service";
import {FormBuilder} from "@angular/forms";
import {Postulacion} from "../../../entities/postulacion";
import {LocalStorageVariables} from "../../../share/local-storage-variables";
import {PostulacionService} from "../../../services/ofertas/postulacion.service";

@Component({
  selector: 'app-oferta-postulaciones-view',
  templateUrl: './oferta-postulaciones-view.component.html',
  styleUrls: ['./oferta-postulaciones-view.component.css']
})
export class OfertaPostulacionesViewComponent implements OnInit{

  postulaciones : Postulacion[] = []
  codigo! : string;

  constructor(private route: ActivatedRoute,
              private postulacionService : PostulacionService,
              private fB: FormBuilder) {
    this.route.paramMap.subscribe((params) => {
      if (params){
        this.codigo = params.get('codigo') ?? "";
      }
    })
  }

  ngOnInit(): void {
    this.postulacionService.getPostulacionesByEmpleador(
        localStorage.getItem(LocalStorageVariables.LOCAL_USER) ?? "",
        this.codigo)
        .subscribe((postulaciones) =>{
          this.postulaciones = postulaciones;
        })
  }


}
