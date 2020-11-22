import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptAnswer } from 'app/shared/model/concepts/concept-answer.model';
import { ConceptAnswerService } from './concept-answer.service';

@Component({
  templateUrl: './concept-answer-delete-dialog.component.html',
})
export class ConceptAnswerDeleteDialogComponent {
  conceptAnswer?: IConceptAnswer;

  constructor(
    protected conceptAnswerService: ConceptAnswerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptAnswerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conceptAnswerListModification');
      this.activeModal.close();
    });
  }
}
