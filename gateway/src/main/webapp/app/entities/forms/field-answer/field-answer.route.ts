import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFieldAnswer, FieldAnswer } from 'app/shared/model/forms/field-answer.model';
import { FieldAnswerService } from './field-answer.service';
import { FieldAnswerComponent } from './field-answer.component';
import { FieldAnswerDetailComponent } from './field-answer-detail.component';
import { FieldAnswerUpdateComponent } from './field-answer-update.component';

@Injectable({ providedIn: 'root' })
export class FieldAnswerResolve implements Resolve<IFieldAnswer> {
  constructor(private service: FieldAnswerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFieldAnswer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fieldAnswer: HttpResponse<FieldAnswer>) => {
          if (fieldAnswer.body) {
            return of(fieldAnswer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FieldAnswer());
  }
}

export const fieldAnswerRoute: Routes = [
  {
    path: '',
    component: FieldAnswerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsFieldAnswer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FieldAnswerDetailComponent,
    resolve: {
      fieldAnswer: FieldAnswerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsFieldAnswer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FieldAnswerUpdateComponent,
    resolve: {
      fieldAnswer: FieldAnswerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsFieldAnswer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FieldAnswerUpdateComponent,
    resolve: {
      fieldAnswer: FieldAnswerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.formsFieldAnswer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
