import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConceptName, ConceptName } from 'app/shared/model/concepts/concept-name.model';
import { ConceptNameService } from './concept-name.service';
import { ConceptNameComponent } from './concept-name.component';
import { ConceptNameDetailComponent } from './concept-name-detail.component';
import { ConceptNameUpdateComponent } from './concept-name-update.component';

@Injectable({ providedIn: 'root' })
export class ConceptNameResolve implements Resolve<IConceptName> {
  constructor(private service: ConceptNameService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConceptName> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conceptName: HttpResponse<ConceptName>) => {
          if (conceptName.body) {
            return of(conceptName.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConceptName());
  }
}

export const conceptNameRoute: Routes = [
  {
    path: '',
    component: ConceptNameComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptName.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptNameDetailComponent,
    resolve: {
      conceptName: ConceptNameResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptName.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptNameUpdateComponent,
    resolve: {
      conceptName: ConceptNameResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptName.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptNameUpdateComponent,
    resolve: {
      conceptName: ConceptNameResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptName.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
