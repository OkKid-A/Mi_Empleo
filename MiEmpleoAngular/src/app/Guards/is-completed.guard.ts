import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {ElectorService} from "../../services/Usuarios/elector.service";

export const isCompletedGuard: CanActivateFn = async (route, state) => {
  let electorService = inject(ElectorService);
  let bool = await electorService.revisarUsuario().toPromise();
  if (bool === undefined) {
    let router = inject(Router);
    return router.createUrlTree(['/error']);
  } else {
    console.log(bool)
    return bool;
  }
}
