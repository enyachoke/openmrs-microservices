import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDrugOrder } from 'app/shared/model/orders/drug-order.model';
import { DrugOrderService } from './drug-order.service';
import { DrugOrderDeleteDialogComponent } from './drug-order-delete-dialog.component';

@Component({
  selector: 'jhi-drug-order',
  templateUrl: './drug-order.component.html',
})
export class DrugOrderComponent implements OnInit, OnDestroy {
  drugOrders?: IDrugOrder[];
  eventSubscriber?: Subscription;

  constructor(protected drugOrderService: DrugOrderService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.drugOrderService.query().subscribe((res: HttpResponse<IDrugOrder[]>) => (this.drugOrders = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDrugOrders();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDrugOrder): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDrugOrders(): void {
    this.eventSubscriber = this.eventManager.subscribe('drugOrderListModification', () => this.loadAll());
  }

  delete(drugOrder: IDrugOrder): void {
    const modalRef = this.modalService.open(DrugOrderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.drugOrder = drugOrder;
  }
}
