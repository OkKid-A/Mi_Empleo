import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ApiUrl} from "../../share/api-url";

@Injectable({
  providedIn: 'root'
})
export class ComisionService {

  readonly COMIS_URL = "comision"

  constructor(private http:HttpClient) { }

  public sumarComision(codigoOferta : string):Observable<number>{
    return this.http.post<number>(ApiUrl.API_URL+this.COMIS_URL,codigoOferta);
  }

  public cambiarComision(valor: string):Observable<void>{
    return this.http.put<void>(ApiUrl.API_URL+this.COMIS_URL,valor);
  }

  public getComision():Observable<number>{
    return this.http.get<number>(ApiUrl.API_URL+this.COMIS_URL);
  }
}
