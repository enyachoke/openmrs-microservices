import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConceptWord, ConceptWord } from 'app/shared/model/concepts/concept-word.model';
import { ConceptWordService } from './concept-word.service';
import { ConceptWordComponent } from './concept-word.component';
import { ConceptWordDetailComponent } from './concept-word-detail.component';
import { ConceptWordUpdateComponent } from './concept-word-update.component';

@Injectable({ providedIn: 'root' })
export class ConceptWordResolve implements Resolve<IConceptWord> {
  constructor(private service: ConceptWordService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConceptWord> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conceptWord: HttpResponse<ConceptWord>) => {
          if (conceptWord.body) {
            return of(conceptWord.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConceptWord());
  }
}

export const conceptWordRoute: Routes = [
  {
    path: '',
    component: ConceptWordComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptWord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConceptWordDetailComponent,
    resolve: {
      conceptWord: ConceptWordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptWord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConceptWordUpdateComponent,
    resolve: {
      conceptWord: ConceptWordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptWord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConceptWordUpdateComponent,
    resolve: {
      conceptWord: ConceptWordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.conceptsConceptWord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
