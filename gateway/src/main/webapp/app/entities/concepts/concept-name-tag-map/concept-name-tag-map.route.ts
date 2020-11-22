import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConceptNameTagMap, ConceptNameTagMap } from 'app/shared/model/concepts/concept-name-tag-map.model';
import { ConceptNameTagMapService } from './concept-name-tag-map.service';
import { ConceptNameTagMapComponent } from './concept-name-tag-map.component';
import { ConceptNameTagMapDetailComponent } from './concept-name-tag-map-detail.component';
import { ConceptNameTagMapUpdateComponent } from './concept-name-tag-map-update.component';

@Injectable({ providedIn: 'root' })
export class ConceptNameTagMapResolve implements Resolve<IConceptNameTagMap> {
  constructor(private service: ConceptNameTagMapService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConceptNameTagMap> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conceptNameTagMap: HttpResponse<ConceptNameTagMap>) => {
          if (conceptNameTagMap.body) {
            return of(conceptNameTagMap.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConceptNameTagMap());
  }
}

export const conceptNameTagMapRoute: Routes = [
  {
    path: '',
    component: ConceptNameTagMapComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptNameTagMap.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptNameTagMapDetailComponent,
    resolve: {
      conceptNameTagMap: ConceptNameTagMapResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptNameTagMap.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptNameTagMapUpdateComponent,
    resolve: {
      conceptNameTagMap: ConceptNameTagMapResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptNameTagMap.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptNameTagMapUpdateComponent,
    resolve: {
      conceptNameTagMap: ConceptNameTagMapResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptNameTagMap.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
