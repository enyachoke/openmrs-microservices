import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFormField, FormField } from 'app/shared/model/forms/form-field.model';
import { FormFieldService } from './form-field.service';
import { FormFieldComponent } from './form-field.component';
import { FormFieldDetailComponent } from './form-field-detail.component';
import { FormFieldUpdateComponent } from './form-field-update.component';

@Injectable({ providedIn: 'root' })
export class FormFieldResolve implements Resolve<IFormField> {
  constructor(private service: FormFieldService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormField> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((formField: HttpResponse<FormField>) => {
          if (formField.body) {
            return of(formField.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FormField());
  }
}

export const formFieldRoute: Routes = [
  {
    path: '',
    component: FormFieldComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsFormField.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FormFieldDetailComponent,
    resolve: {
      formField: FormFieldResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsFormField.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FormFieldUpdateComponent,
    resolve: {
      formField: FormFieldResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsFormField.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FormFieldUpdateComponent,
    resolve: {
      formField: FormFieldResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsFormField.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
