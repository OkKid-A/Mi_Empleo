import { CanActivateFn } from '@angular/router';
import {inject} from "@angular/core";
import {ElectorService} from "../../services/Usuarios/elector.service";

export const isCompletedGuard: CanActivateFn = (route, state) => {
  let electorService = inject(ElectorService);
  return electorService.revisarUsuario()
}
