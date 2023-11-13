import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiUrl} from "../../share/api-url";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  readonly NOT_URL = "notificacion";

  constructor(private http: HttpClient) {

  }

  enviarEmailConOferta(oferta: number, subject: string, mensaje: string){
    return this.http.get(ApiUrl.API_URL+this.NOT_URL+"?oferta="+oferta+"&subject="+subject+"&mensaje="+mensaje);
  }

  enviarEmailAUsuariop(codigo: string, subject: string, mensaje: string){
    return this.http.get(ApiUrl.API_URL+this.NOT_URL+"?usuario="+codigo+"&subject="+subject+"&mensaje="+mensaje);
  }

  enviarEmailAllOferta(codigo: number, subject:string, mensaje:string, estado:string){
    return this.http.put(ApiUrl.API_URL+this.NOT_URL+"?oferta="+codigo+"&subject="+subject+"&mensaje="+mensaje,estado);

  }

}
