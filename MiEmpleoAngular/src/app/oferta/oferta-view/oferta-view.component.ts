import {Component, OnInit} from '@angular/core';
import {Oferta} from "../../../entities/oferta";
import {OfertaService} from "../../../services/ofertas/oferta.service";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SolicitudService} from "../../../services/ofertas/solicitud.service";
import {Solicitud} from "../../../entities/solicitud";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-oferta-view',
  templateUrl: './oferta-view.component.html',
  styleUrls: ['./oferta-view.component.css']
})
export class OfertaViewComponent implements OnInit{
  errores! : string
  codigo! : string
  oferta! : Oferta;
  aplicarForm! : FormGroup
  solicitud! : Solicitud
  success! : boolean
  constructor(private route: ActivatedRoute,
              private  ofertaSerice : OfertaService,
              private solicitudServices: SolicitudService,
              private fB: FormBuilder) {
     this.route.paramMap.subscribe((params) => {
       if (params){
         this.codigo = params.get('codigo') ?? "";
       }
     })
  }

  ngOnInit(): void {
    this.ofertaSerice.getOfertaById(this.codigo).subscribe((oferta : Oferta) => {
      this.oferta = oferta;
    })
    this.aplicarForm = this.fB.group({
      codigo_oferta : this.codigo,
        usuario: localStorage.getItem("usuarioActual"),
      mensaje : [null,[Validators.required, Validators.maxLength(256)]]
    });
  }

  aplicarSubmit(): void{
    this.solicitud = this.aplicarForm.value as Solicitud;
    this.solicitudServices.aplicar(this.solicitud).subscribe( (solicitud) =>
    {console.log()
    this.success = true}
      ,
      (err:HttpErrorResponse) =>{
      alert(err.message)
        this.aplicarForm.reset()
      }
    );
  }

}
