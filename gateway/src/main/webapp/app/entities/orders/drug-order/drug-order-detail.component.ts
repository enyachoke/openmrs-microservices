import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDrugOrder } from 'app/shared/model/orders/drug-order.model';

@Component({
  selector: 'jhi-drug-order-detail',
  templateUrl: './drug-order-detail.component.html',
})
export class DrugOrderDetailComponent implements OnInit {
  drugOrder: IDrugOrder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ drugOrder }) => (this.drugOrder = drugOrder));
  }

  previousState(): void {
    window.history.back();
  }
}
