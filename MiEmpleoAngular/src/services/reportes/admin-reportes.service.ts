import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ApiUrl} from "../../share/api-url";
import {LocalStorageVariables} from "../../share/local-storage-variables";

@Injectable({
  providedIn: 'root'
})
export class AdminReportesService {

  readonly ADMREP_URL = "admin-reportes";

  constructor(private http: HttpClient) { }

  public getAdminReport(tipo: number){
    const admin = localStorage.getItem(LocalStorageVariables.LOCAL_USER);
    return this.http.get(ApiUrl.API_URL+this.ADMREP_URL+"?tipo="+tipo+"&admin="+admin,{responseType: 'blob'});
  }

  public getAdminReportWithDates(tipo: number, fechaInicial:string, fechaFinal : string){
    const admin = localStorage.getItem(LocalStorageVariables.LOCAL_USER);
    return this.http.get(ApiUrl.API_URL+this.ADMREP_URL+"?tipo="+tipo+"&admin="+admin+"&fechaInicial="+fechaInicial+"&fechaFinal="+fechaFinal
      ,{responseType: 'blob'});
  }
}
