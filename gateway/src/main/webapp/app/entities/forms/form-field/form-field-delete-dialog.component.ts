import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFormField } from 'app/shared/model/forms/form-field.model';
import { FormFieldService } from './form-field.service';

@Component({
  templateUrl: './form-field-delete-dialog.component.html',
})
export class FormFieldDeleteDialogComponent {
  formField?: IFormField;

  constructor(protected formFieldService: FormFieldService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formFieldService.delete(id).subscribe(() => {
      this.eventManager.broadcast('formFieldListModification');
      this.activeModal.close();
    });
  }
}
