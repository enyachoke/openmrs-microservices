import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConceptSet, ConceptSet } from 'app/shared/model/concepts/concept-set.model';
import { ConceptSetService } from './concept-set.service';
import { ConceptSetComponent } from './concept-set.component';
import { ConceptSetDetailComponent } from './concept-set-detail.component';
import { ConceptSetUpdateComponent } from './concept-set-update.component';

@Injectable({ providedIn: 'root' })
export class ConceptSetResolve implements Resolve<IConceptSet> {
  constructor(private service: ConceptSetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConceptSet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conceptSet: HttpResponse<ConceptSet>) => {
          if (conceptSet.body) {
            return of(conceptSet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConceptSet());
  }
}

export const conceptSetRoute: Routes = [
  {
    path: '',
    component: ConceptSetComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptSet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptSetDetailComponent,
    resolve: {
      conceptSet: ConceptSetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptSet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptSetUpdateComponent,
    resolve: {
      conceptSet: ConceptSetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptSet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptSetUpdateComponent,
    resolve: {
      conceptSet: ConceptSetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptSet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
