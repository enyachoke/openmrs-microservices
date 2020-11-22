import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptReferenceTerm } from 'app/shared/model/concepts/concept-reference-term.model';
import { ConceptReferenceTermService } from './concept-reference-term.service';

@Component({
  templateUrl: './concept-reference-term-delete-dialog.component.html',
})
export class ConceptReferenceTermDeleteDialogComponent {
  conceptReferenceTerm?: IConceptReferenceTerm;

  constructor(
    protected conceptReferenceTermService: ConceptReferenceTermService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptReferenceTermService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conceptReferenceTermListModification');
      this.activeModal.close();
    });
  }
}
