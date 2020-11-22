import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConceptComplex, ConceptComplex } from 'app/shared/model/concepts/concept-complex.model';
import { ConceptComplexService } from './concept-complex.service';
import { ConceptComplexComponent } from './concept-complex.component';
import { ConceptComplexDetailComponent } from './concept-complex-detail.component';
import { ConceptComplexUpdateComponent } from './concept-complex-update.component';

@Injectable({ providedIn: 'root' })
export class ConceptComplexResolve implements Resolve<IConceptComplex> {
  constructor(private service: ConceptComplexService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConceptComplex> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conceptComplex: HttpResponse<ConceptComplex>) => {
          if (conceptComplex.body) {
            return of(conceptComplex.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConceptComplex());
  }
}

export const conceptComplexRoute: Routes = [
  {
    path: '',
    component: ConceptComplexComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptComplex.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptComplexDetailComponent,
    resolve: {
      conceptComplex: ConceptComplexResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptComplex.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptComplexUpdateComponent,
    resolve: {
      conceptComplex: ConceptComplexResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptComplex.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptComplexUpdateComponent,
    resolve: {
      conceptComplex: ConceptComplexResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptComplex.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
