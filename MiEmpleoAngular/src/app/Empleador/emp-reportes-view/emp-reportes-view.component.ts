import { Component } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {AdminReportesService} from "../../../services/reportes/admin-reportes.service";
import {LocalStorageVariables} from "../../../share/local-storage-variables";
import {HttpErrorResponse} from "@angular/common/http";
import {EmpReportesService} from "../../../services/reportes/emp-reportes.service";

@Component({
  selector: 'app-emp-reportes-view',
  templateUrl: './emp-reportes-view.component.html',
  styleUrls: ['./emp-reportes-view.component.css']
})
export class EmpReportesViewComponent {

  reportExists : boolean = false;
  reportUrl! : string;
  rutaReporte! : string;
  tipo! : number
  estado! :  string;
  reportForm = this.fB.nonNullable.group({
    tipo :0,
    estado : "",
    fechaInicial : [null,[Validators.required]],
    fechaFinal: [null,[Validators.required]]
  });
  constructor(private fB: FormBuilder,
              private empReportes : EmpReportesService) {

  }

  ngOnInit(): void {

  }


  onCrearSubmit(){
    const mainUrl = "http://localhost:8080/Mi_Empleo_Api_war_exploded/emp-reportes";
    this.reportUrl = mainUrl+"?tipo="+this.reportForm.value.tipo+"&estado="+this.reportForm.value.estado+"&emp="+
      localStorage.getItem(LocalStorageVariables.LOCAL_USER);
    if (this.reportForm.value.tipo==0||this.reportForm.value.tipo==1){
      this.reportUrl = this.reportUrl + "&fechaInicial="+this.reportForm.value.fechaInicial+"&fechaFinal="+
        this.reportForm.value.fechaFinal;
    }
    this.reportExists = true;
  }

  changeEstado(){
    this.estado = this.reportForm.value.estado ?? "";
  }


  changeReport(){
    this.tipo = this.reportForm.value.tipo ?? 0;
  }

  descargarReport(){
    this.empReportes.getOfertasReport(this.reportForm.value.tipo??0,this.reportForm.value.estado??"",
      this.reportForm.value.fechaInicial??"",this.reportForm.value.fechaFinal??"").subscribe({
      next: (blob)=>{
        this.descargar(blob)
      }, error: (err: HttpErrorResponse) => {
        alert(err.error.message);
      }
    });
  }

  descargar(blob: Blob){
    const url = window.URL.createObjectURL(blob);
    const anchor = document.createElement('a');
    anchor.href = url;
    switch (this.tipo){
      case 0:
        anchor.download = "HistorialOfertas.pdf";
        break;
      case 1:
        anchor.download = "Top5Ganancias.pdf";
        break;
      case 2:
        anchor.download = "GananciasPorCategorias.pdf";
        break;
      default:
        anchor.download = "Reporte.pdf";
    }
    document.body.appendChild(anchor);
    anchor.click();
    document.body.removeChild(anchor);
  }
}
