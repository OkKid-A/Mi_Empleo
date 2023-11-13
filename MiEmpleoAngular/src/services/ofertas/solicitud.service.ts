import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, Observable, of, throwError} from "rxjs";
import {Solicitud} from "../../entities/solicitud";
import {ApiUrl} from "../../share/api-url";
import {Postulacion} from "../../entities/postulacion";
import {Oferta} from "../../entities/oferta";
import {NotificationService} from "../notification/notification.service";

@Injectable({
  providedIn: 'root'
})
export class SolicitudService {
  API_SERVICE = "solicitud"

  constructor(private http: HttpClient) { }

  public getSolicitudesByEstado(estado: string) : Observable<Oferta[]> {
    let userId = localStorage.getItem("usuarioActual");
    return this.http.get<Oferta[]>(ApiUrl.API_URL + this.API_SERVICE + "?estado=" + estado+ "&usuario="+userId).pipe(
    )
  }

  public aplicar(solicitud:Solicitud ) : Observable<Solicitud> {
    return this.http.post<Solicitud>(ApiUrl.API_URL+this.API_SERVICE,solicitud).pipe(
      catchError(this.manejarError)
    );
  }

  public borrar(oferta: Oferta){
    return this.http.delete(ApiUrl.API_URL+this.API_SERVICE+"?oferta="+oferta.codigo+"&usuario="+
    localStorage.getItem("usuarioActual")).pipe(

      catchError(this.manejarError)
    );
  }

    private manejarError(error: HttpErrorResponse){
    if (error.status === 0){
      console.error("Ocurrio un error",error.error)
    } else if (error.status === 404){
      console.error("Ya existe una solicitud para esta oferta",error.error)
    } else if (error.status === 400){
      console.error("Se enviaron datos incorrectos",error.error)
    }
      return throwError(error);
    }
}
