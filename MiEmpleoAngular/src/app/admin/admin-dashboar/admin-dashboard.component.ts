import {Component, OnInit} from '@angular/core';
import {DashboardService} from "../../../services/reportes/dashboard.service";
import {Dashboard} from "../../../entities/dashboard";

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements  OnInit{

  solicitantes !: number;
  empleadores !: number;
  visitas : number = 0;
  ganancias !: number;

  constructor(private dashboardService: DashboardService) {

  }

  ngOnInit(): void {
    this.dashboardService.getDashboard().subscribe({
      next:(dashboard:Dashboard) =>{
        this.solicitantes = dashboard.solicitantes;
        this.empleadores = dashboard.empleadores;
        this.ganancias = dashboard.ganancias;
      }
    })
  }
}
