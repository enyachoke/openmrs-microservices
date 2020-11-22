import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptDescription } from 'app/shared/model/concepts/concept-description.model';
import { ConceptDescriptionService } from './concept-description.service';

@Component({
  templateUrl: './concept-description-delete-dialog.component.html',
})
export class ConceptDescriptionDeleteDialogComponent {
  conceptDescription?: IConceptDescription;

  constructor(
    protected conceptDescriptionService: ConceptDescriptionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptDescriptionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conceptDescriptionListModification');
      this.activeModal.close();
    });
  }
}
