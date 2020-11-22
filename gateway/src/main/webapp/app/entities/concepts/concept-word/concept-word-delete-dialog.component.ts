import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptWord } from 'app/shared/model/concepts/concept-word.model';
import { ConceptWordService } from './concept-word.service';

@Component({
  templateUrl: './concept-word-delete-dialog.component.html',
})
export class ConceptWordDeleteDialogComponent {
  conceptWord?: IConceptWord;

  constructor(
    protected conceptWordService: ConceptWordService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptWordService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conceptWordListModification');
      this.activeModal.close();
    });
  }
}
