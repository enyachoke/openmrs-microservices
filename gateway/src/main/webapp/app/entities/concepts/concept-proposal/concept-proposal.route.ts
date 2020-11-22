import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConceptProposal, ConceptProposal } from 'app/shared/model/concepts/concept-proposal.model';
import { ConceptProposalService } from './concept-proposal.service';
import { ConceptProposalComponent } from './concept-proposal.component';
import { ConceptProposalDetailComponent } from './concept-proposal-detail.component';
import { ConceptProposalUpdateComponent } from './concept-proposal-update.component';

@Injectable({ providedIn: 'root' })
export class ConceptProposalResolve implements Resolve<IConceptProposal> {
  constructor(private service: ConceptProposalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConceptProposal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conceptProposal: HttpResponse<ConceptProposal>) => {
          if (conceptProposal.body) {
            return of(conceptProposal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConceptProposal());
  }
}

export const conceptProposalRoute: Routes = [
  {
    path: '',
    component: ConceptProposalComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptProposal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptProposalDetailComponent,
    resolve: {
      conceptProposal: ConceptProposalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptProposal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptProposalUpdateComponent,
    resolve: {
      conceptProposal: ConceptProposalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptProposal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptProposalUpdateComponent,
    resolve: {
      conceptProposal: ConceptProposalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptProposal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
