import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AdminReportesService} from "../../../services/reportes/admin-reportes.service";
import {HttpErrorResponse} from "@angular/common/http";
import {LocalStorageVariables} from "../../../share/local-storage-variables";

@Component({
  selector: 'app-admin-reportes-view',
  templateUrl: './admin-reportes-view.component.html',
  styleUrls: ['./admin-reportes-view.component.css']
})
export class AdminReportesViewComponent implements OnInit{

  reportExists : boolean = false;
  reportUrl! : string;
  rutaReporte! : string;
  tipo! : number
  reporte! : File
  reportForm = this.fB.nonNullable.group({
    tipo :0,
    estado : 0,
    fechaInicial : [null,[Validators.required]],
    fechaFinal: [null,[Validators.required]]
  });
  constructor(private fB: FormBuilder,
              private adminReportes : AdminReportesService) {

  }

  ngOnInit(): void {

  }


  onCrearSubmit(){
    const mainUrl = "http://localhost:8080/Mi_Empleo_Api_war_exploded/admin-reportes";
    this.reportUrl = mainUrl+"?tipo="+this.reportForm.value.tipo+"&admin="+
      localStorage.getItem(LocalStorageVariables.LOCAL_USER);
    if (this.reportForm.value.tipo==1||this.reportForm.value.tipo==2){
      this.reportUrl = this.reportUrl + "&fechaInicial="+this.reportForm.value.fechaInicial+"&fechaFinal="+
        this.reportForm.value.fechaFinal;
    }
    this.reportExists = true;
  }

  changeReport(){
    this.tipo = this.reportForm.value.tipo ?? 0;
  }

  descargarReport(){
    if (this.reportForm.value.tipo == 1|| this.reportForm.value.tipo == 2){
      this.adminReportes.getAdminReportWithDates(this.reportForm.value.tipo??"",
        this.reportForm.value.fechaInicial??"",this.reportForm.value.fechaFinal??"").subscribe({
        next: (blob) =>{
          this.descargar(blob)
        }, error: (err: HttpErrorResponse) => {
          alert(err.error.message);
        }
      });
    } else {
      this.adminReportes.getAdminReport(this.reportForm.value.tipo ?? 0).subscribe({
        next: (blob) => {
          this.descargar(blob)
        }, error: (err: HttpErrorResponse) => {
          alert(err.error.message);
        }
      })
    }
  }

  descargar(blob: Blob){
    const url = window.URL.createObjectURL(blob);
    const anchor = document.createElement('a');
    anchor.href = url;
    switch (this.tipo){
      case 0:
        anchor.download = "Top5EmpOfertas.pdf";
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

