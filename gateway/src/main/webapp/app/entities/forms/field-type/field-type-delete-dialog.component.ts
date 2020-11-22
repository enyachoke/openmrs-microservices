import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFieldType } from 'app/shared/model/forms/field-type.model';
import { FieldTypeService } from './field-type.service';

@Component({
  templateUrl: './field-type-delete-dialog.component.html',
})
export class FieldTypeDeleteDialogComponent {
  fieldType?: IFieldType;

  constructor(protected fieldTypeService: FieldTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fieldTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fieldTypeListModification');
      this.activeModal.close();
    });
  }
}
