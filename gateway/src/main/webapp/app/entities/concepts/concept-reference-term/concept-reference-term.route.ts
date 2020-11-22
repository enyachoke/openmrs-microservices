import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConceptReferenceTerm, ConceptReferenceTerm } from 'app/shared/model/concepts/concept-reference-term.model';
import { ConceptReferenceTermService } from './concept-reference-term.service';
import { ConceptReferenceTermComponent } from './concept-reference-term.component';
import { ConceptReferenceTermDetailComponent } from './concept-reference-term-detail.component';
import { ConceptReferenceTermUpdateComponent } from './concept-reference-term-update.component';

@Injectable({ providedIn: 'root' })
export class ConceptReferenceTermResolve implements Resolve<IConceptReferenceTerm> {
  constructor(private service: ConceptReferenceTermService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConceptReferenceTerm> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conceptReferenceTerm: HttpResponse<ConceptReferenceTerm>) => {
          if (conceptReferenceTerm.body) {
            return of(conceptReferenceTerm.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConceptReferenceTerm());
  }
}

export const conceptReferenceTermRoute: Routes = [
  {
    path: '',
    component: ConceptReferenceTermComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptReferenceTerm.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptReferenceTermDetailComponent,
    resolve: {
      conceptReferenceTerm: ConceptReferenceTermResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptReferenceTerm.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptReferenceTermUpdateComponent,
    resolve: {
      conceptReferenceTerm: ConceptReferenceTermResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptReferenceTerm.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptReferenceTermUpdateComponent,
    resolve: {
      conceptReferenceTerm: ConceptReferenceTermResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptReferenceTerm.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
