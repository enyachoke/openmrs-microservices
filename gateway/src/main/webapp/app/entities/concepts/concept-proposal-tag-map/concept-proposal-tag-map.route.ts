import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConceptProposalTagMap, ConceptProposalTagMap } from 'app/shared/model/concepts/concept-proposal-tag-map.model';
import { ConceptProposalTagMapService } from './concept-proposal-tag-map.service';
import { ConceptProposalTagMapComponent } from './concept-proposal-tag-map.component';
import { ConceptProposalTagMapDetailComponent } from './concept-proposal-tag-map-detail.component';
import { ConceptProposalTagMapUpdateComponent } from './concept-proposal-tag-map-update.component';

@Injectable({ providedIn: 'root' })
export class ConceptProposalTagMapResolve implements Resolve<IConceptProposalTagMap> {
  constructor(private service: ConceptProposalTagMapService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConceptProposalTagMap> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conceptProposalTagMap: HttpResponse<ConceptProposalTagMap>) => {
          if (conceptProposalTagMap.body) {
            return of(conceptProposalTagMap.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConceptProposalTagMap());
  }
}

export const conceptProposalTagMapRoute: Routes = [
  {
    path: '',
    component: ConceptProposalTagMapComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptProposalTagMap.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptProposalTagMapDetailComponent,
    resolve: {
      conceptProposalTagMap: ConceptProposalTagMapResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptProposalTagMap.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptProposalTagMapUpdateComponent,
    resolve: {
      conceptProposalTagMap: ConceptProposalTagMapResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptProposalTagMap.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptProposalTagMapUpdateComponent,
    resolve: {
      conceptProposalTagMap: ConceptProposalTagMapResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptProposalTagMap.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
