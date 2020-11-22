import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDrugOrder, DrugOrder } from 'app/shared/model/orders/drug-order.model';
import { DrugOrderService } from './drug-order.service';
import { DrugOrderComponent } from './drug-order.component';
import { DrugOrderDetailComponent } from './drug-order-detail.component';
import { DrugOrderUpdateComponent } from './drug-order-update.component';

@Injectable({ providedIn: 'root' })
export class DrugOrderResolve implements Resolve<IDrugOrder> {
  constructor(private service: DrugOrderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDrugOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((drugOrder: HttpResponse<DrugOrder>) => {
          if (drugOrder.body) {
            return of(drugOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DrugOrder());
  }
}

export const drugOrderRoute: Routes = [
  {
    path: '',
    component: DrugOrderComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.ordersDrugOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DrugOrderDetailComponent,
    resolve: {
      drugOrder: DrugOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.ordersDrugOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DrugOrderUpdateComponent,
    resolve: {
      drugOrder: DrugOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.ordersDrugOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DrugOrderUpdateComponent,
    resolve: {
      drugOrder: DrugOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.ordersDrugOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
