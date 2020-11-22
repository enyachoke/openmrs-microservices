import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDrugOrder } from 'app/shared/model/orders/drug-order.model';
import { DrugOrderService } from './drug-order.service';

@Component({
  templateUrl: './drug-order-delete-dialog.component.html',
})
export class DrugOrderDeleteDialogComponent {
  drugOrder?: IDrugOrder;

  constructor(protected drugOrderService: DrugOrderService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.drugOrderService.delete(id).subscribe(() => {
      this.eventManager.broadcast('drugOrderListModification');
      this.activeModal.close();
    });
  }
}
