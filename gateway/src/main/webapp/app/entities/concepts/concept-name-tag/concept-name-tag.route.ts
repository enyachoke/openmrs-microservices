import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConceptNameTag, ConceptNameTag } from 'app/shared/model/concepts/concept-name-tag.model';
import { ConceptNameTagService } from './concept-name-tag.service';
import { ConceptNameTagComponent } from './concept-name-tag.component';
import { ConceptNameTagDetailComponent } from './concept-name-tag-detail.component';
import { ConceptNameTagUpdateComponent } from './concept-name-tag-update.component';

@Injectable({ providedIn: 'root' })
export class ConceptNameTagResolve implements Resolve<IConceptNameTag> {
  constructor(private service: ConceptNameTagService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConceptNameTag> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conceptNameTag: HttpResponse<ConceptNameTag>) => {
          if (conceptNameTag.body) {
            return of(conceptNameTag.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConceptNameTag());
  }
}

export const conceptNameTagRoute: Routes = [
  {
    path: '',
    component: ConceptNameTagComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptNameTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptNameTagDetailComponent,
    resolve: {
      conceptNameTag: ConceptNameTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptNameTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptNameTagUpdateComponent,
    resolve: {
      conceptNameTag: ConceptNameTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptNameTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptNameTagUpdateComponent,
    resolve: {
      conceptNameTag: ConceptNameTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptNameTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
