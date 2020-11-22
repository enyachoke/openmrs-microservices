import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFieldAnswer } from 'app/shared/model/forms/field-answer.model';
import { FieldAnswerService } from './field-answer.service';

@Component({
  templateUrl: './field-answer-delete-dialog.component.html',
})
export class FieldAnswerDeleteDialogComponent {
  fieldAnswer?: IFieldAnswer;

  constructor(
    protected fieldAnswerService: FieldAnswerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fieldAnswerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fieldAnswerListModification');
      this.activeModal.close();
    });
  }
}
