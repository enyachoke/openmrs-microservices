import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFieldType, FieldType } from 'app/shared/model/forms/field-type.model';
import { FieldTypeService } from './field-type.service';
import { FieldTypeComponent } from './field-type.component';
import { FieldTypeDetailComponent } from './field-type-detail.component';
import { FieldTypeUpdateComponent } from './field-type-update.component';

@Injectable({ providedIn: 'root' })
export class FieldTypeResolve implements Resolve<IFieldType> {
  constructor(private service: FieldTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFieldType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fieldType: HttpResponse<FieldType>) => {
          if (fieldType.body) {
            return of(fieldType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FieldType());
  }
}

export const fieldTypeRoute: Routes = [
  {
    path: '',
    component: FieldTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsFieldType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FieldTypeDetailComponent,
    resolve: {
      fieldType: FieldTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsFieldType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FieldTypeUpdateComponent,
    resolve: {
      fieldType: FieldTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsFieldType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FieldTypeUpdateComponent,
    resolve: {
      fieldType: FieldTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsFieldType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
