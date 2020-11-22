import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderType } from 'app/shared/model/orders/order-type.model';

@Component({
  selector: 'jhi-order-type-detail',
  templateUrl: './order-type-detail.component.html',
})
export class OrderTypeDetailComponent implements OnInit {
  orderType: IOrderType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderType }) => (this.orderType = orderType));
  }

  previousState(): void {
    window.history.back();
  }
}
