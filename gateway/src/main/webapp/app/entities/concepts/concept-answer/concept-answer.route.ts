import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConceptAnswer, ConceptAnswer } from 'app/shared/model/concepts/concept-answer.model';
import { ConceptAnswerService } from './concept-answer.service';
import { ConceptAnswerComponent } from './concept-answer.component';
import { ConceptAnswerDetailComponent } from './concept-answer-detail.component';
import { ConceptAnswerUpdateComponent } from './concept-answer-update.component';

@Injectable({ providedIn: 'root' })
export class ConceptAnswerResolve implements Resolve<IConceptAnswer> {
  constructor(private service: ConceptAnswerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConceptAnswer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conceptAnswer: HttpResponse<ConceptAnswer>) => {
          if (conceptAnswer.body) {
            return of(conceptAnswer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConceptAnswer());
  }
}

export const conceptAnswerRoute: Routes = [
  {
    path: '',
    component: ConceptAnswerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptAnswer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptAnswerDetailComponent,
    resolve: {
      conceptAnswer: ConceptAnswerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptAnswer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptAnswerUpdateComponent,
    resolve: {
      conceptAnswer: ConceptAnswerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptAnswer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptAnswerUpdateComponent,
    resolve: {
      conceptAnswer: ConceptAnswerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptAnswer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
