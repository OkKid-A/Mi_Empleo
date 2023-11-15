import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Entrevista} from "../../entities/entrevista";
import {Observable} from "rxjs";
import {ApiUrl} from "../../share/api-url";

@Injectable({
  providedIn: 'root'
})
export class EntrevistaService {

  readonly ENTRE_URL = "entrevista"

  constructor(private http: HttpClient) { }

  public createEntrevista(entrevista: Entrevista): Observable<Entrevista>{
    return this.http.post<Entrevista>(ApiUrl.API_URL+this.ENTRE_URL,entrevista)
  }

  public tieneEntrevista(codigoOferta: string): Observable<boolean>{
    return this.http.get<boolean>(ApiUrl.API_URL+this.ENTRE_URL+"?oferta="+codigoOferta);
  }

  public getEntrevistasOferta(codigoOferta: string): Observable<Entrevista[]>{
    return this.http.get<Entrevista[]>(ApiUrl.API_URL+this.ENTRE_URL+"?ofertas="+codigoOferta);
  }

  public getEntrevistasUsuario(usuario: string): Observable<Entrevista[]>{
    return this.http.get<Entrevista[]>(ApiUrl.API_URL+this.ENTRE_URL+"?usuario="+usuario);
  }

  public finalizarEntrevista(entrevista: Entrevista, notas: string): Observable<Entrevista>{
    return this.http.put<Entrevista>(ApiUrl.API_URL+this.ENTRE_URL+"?notas="+notas,entrevista)
  }
}
