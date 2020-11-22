import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConceptNumeric, ConceptNumeric } from 'app/shared/model/concepts/concept-numeric.model';
import { ConceptNumericService } from './concept-numeric.service';
import { ConceptNumericComponent } from './concept-numeric.component';
import { ConceptNumericDetailComponent } from './concept-numeric-detail.component';
import { ConceptNumericUpdateComponent } from './concept-numeric-update.component';

@Injectable({ providedIn: 'root' })
export class ConceptNumericResolve implements Resolve<IConceptNumeric> {
  constructor(private service: ConceptNumericService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConceptNumeric> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conceptNumeric: HttpResponse<ConceptNumeric>) => {
          if (conceptNumeric.body) {
            return of(conceptNumeric.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConceptNumeric());
  }
}

export const conceptNumericRoute: Routes = [
  {
    path: '',
    component: ConceptNumericComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptNumeric.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptNumericDetailComponent,
    resolve: {
      conceptNumeric: ConceptNumericResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptNumeric.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptNumericUpdateComponent,
    resolve: {
      conceptNumeric: ConceptNumericResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptNumeric.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptNumericUpdateComponent,
    resolve: {
      conceptNumeric: ConceptNumericResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptNumeric.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
