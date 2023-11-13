import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Dashboard} from "../../entities/dashboard";
import {ApiUrl} from "../../share/api-url";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  readonly DASH_URL = "resumen";

  constructor(private http: HttpClient) { }

  public getDashboard():Observable<Dashboard>{
    return this.http.get<Dashboard>(ApiUrl.API_URL+this.DASH_URL);
  }
}
