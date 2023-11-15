import {Component, OnInit, TemplateRef} from '@angular/core';
import {Categoria} from "../../../entities/categoria";
import {OfertaService} from "../../../services/ofertas/oferta.service";
import {of} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CategoriaService} from "../../../services/ofertas/categoria.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-admin-categorias-table',
  templateUrl: './admin-categorias-table.component.html',
  styleUrls: ['./admin-categorias-table.component.css']
})
export class AdminCategoriasTableComponent implements OnInit{

  categorias : Categoria[] = [];
  seleccionada! : Categoria;
  modalRef! : BsModalRef
  categoriaForm!: FormGroup;
  nueva : boolean =false;

  constructor(private ofertaService: OfertaService,
              private modalService: BsModalService,
              private categoriaService: CategoriaService,
              private fB: FormBuilder,
              private router: Router) {
  }

  ngOnInit(): void {
    this.ofertaService.getAllCategorias().subscribe({
      next:(categorias)=>{
        this.categorias = categorias;
      }, error:(err:HttpErrorResponse) =>{
        alert(err.message)
      }
    })
  }

  seleccionarCategoria(categoria: Categoria, template: TemplateRef<any>){
    if (categoria === undefined){
      this.seleccionada = {
        codigo: 0,
        descripcion: '',
        nombre: ''
      }
      this.nueva = true;
      this.categoriaForm = this.fB.group({
        nombre: [null,[Validators.required,Validators.maxLength(20)]],
        descripcion: [null,[Validators.required, Validators.maxLength(500)]],
      })
    } else {
      this.seleccionada = categoria;
      this.categoriaForm = this.fB.group({
        nombre: [null,[Validators.required,Validators.maxLength(20)]],
        descripcion: [null,[Validators.required, Validators.maxLength(500)]],
        codigo: categoria.codigo
      })
    }
    this.openModal(template);
  }

  openModal(template : TemplateRef<any>){
    this.modalRef = this.modalService.show(template)
  }

  editar(){
    const categoria = this.categoriaForm.value as Categoria;
    this.categoriaService.editarCategoria(categoria).subscribe({
      next:()=>{
        if (this.nueva){
          alert("Se ha creado la categoria con exito");
        } else {
          alert("Se ha editado la categoria con exito");
        }
        this.router.navigate([this.router.url])
      }, error:(err:HttpErrorResponse)=>{
        alert(err.message);
      }
    })
  }

}
