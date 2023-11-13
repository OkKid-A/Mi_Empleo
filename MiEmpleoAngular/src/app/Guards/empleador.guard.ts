import { CanActivateFn } from '@angular/router';
import {LocalStorageVariables} from "../../share/local-storage-variables";
import {Roles} from "../../share/roles";

export const empleadorGuard: CanActivateFn = (route, state) => {
  const rol = localStorage.getItem(LocalStorageVariables.PERSMISO_LOCAL);
  return !!(rol && rol == Roles.EMPLEADOR);
};
