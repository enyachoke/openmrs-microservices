import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConceptDescription, ConceptDescription } from 'app/shared/model/concepts/concept-description.model';
import { ConceptDescriptionService } from './concept-description.service';
import { ConceptDescriptionComponent } from './concept-description.component';
import { ConceptDescriptionDetailComponent } from './concept-description-detail.component';
import { ConceptDescriptionUpdateComponent } from './concept-description-update.component';

@Injectable({ providedIn: 'root' })
export class ConceptDescriptionResolve implements Resolve<IConceptDescription> {
  constructor(private service: ConceptDescriptionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConceptDescription> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conceptDescription: HttpResponse<ConceptDescription>) => {
          if (conceptDescription.body) {
            return of(conceptDescription.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConceptDescription());
  }
}

export const conceptDescriptionRoute: Routes = [
  {
    path: '',
    component: ConceptDescriptionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptDescription.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptDescriptionDetailComponent,
    resolve: {
      conceptDescription: ConceptDescriptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptDescription.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptDescriptionUpdateComponent,
    resolve: {
      conceptDescription: ConceptDescriptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptDescription.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptDescriptionUpdateComponent,
    resolve: {
      conceptDescription: ConceptDescriptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptDescription.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
