import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IField } from 'app/shared/model/forms/field.model';
import { FieldService } from './field.service';

@Component({
  templateUrl: './field-delete-dialog.component.html',
})
export class FieldDeleteDialogComponent {
  field?: IField;

  constructor(protected fieldService: FieldService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fieldService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fieldListModification');
      this.activeModal.close();
    });
  }
}
