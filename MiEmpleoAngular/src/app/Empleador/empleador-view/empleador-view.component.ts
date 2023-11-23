import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-empleador-view',
  templateUrl: './empleador-view.component.html',
  styleUrls: ['./empleador-view.component.css']
})
export class EmpleadorViewComponent {

  empleador: boolean = false;

  constructor(private router: Router) {
  }
}
