import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IField, Field } from 'app/shared/model/forms/field.model';
import { FieldService } from './field.service';
import { FieldComponent } from './field.component';
import { FieldDetailComponent } from './field-detail.component';
import { FieldUpdateComponent } from './field-update.component';

@Injectable({ providedIn: 'root' })
export class FieldResolve implements Resolve<IField> {
  constructor(private service: FieldService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IField> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((field: HttpResponse<Field>) => {
          if (field.body) {
            return of(field.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Field());
  }
}

export const fieldRoute: Routes = [
  {
    path: '',
    component: FieldComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsField.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FieldDetailComponent,
    resolve: {
      field: FieldResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsField.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FieldUpdateComponent,
    resolve: {
      field: FieldResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsField.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FieldUpdateComponent,
    resolve: {
      field: FieldResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsField.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
