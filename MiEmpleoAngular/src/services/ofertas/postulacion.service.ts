import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Solicitud} from "../../entities/solicitud";
import {ApiUrl} from "../../share/api-url";
import {Postulacion} from "../../entities/postulacion";
import {Oferta} from "../../entities/oferta";
import {Entrevista} from "../../entities/entrevista";

@Injectable({
  providedIn: 'root'
})
export class PostulacionService {

  LOCAL_API = "postulacion"

  constructor(private http: HttpClient) { }

  public getPostulacionesByEmpleador(empleador: string, oferta: string):Observable<Postulacion[]>{
    return this.http.get<Postulacion[]>(ApiUrl.API_URL+this.LOCAL_API+"?empleador="+empleador+"&oferta="+oferta)
  }

  public cambiarEstadoOferta(oferta: Oferta, nuevoEstado: String):Observable<Oferta>{
    return this.http.put<Oferta>(ApiUrl.API_URL+this.LOCAL_API+ "?estado="+nuevoEstado,oferta)
  }

  public seleccionarGanadorOferta(codigoUsuario : string, codigoOferta : string):Observable<Oferta>{
    return this.http.post<Oferta>(ApiUrl.API_URL+this.LOCAL_API + "?usuario="+codigoUsuario,codigoOferta)
  }
}
