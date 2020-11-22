import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptNumeric } from 'app/shared/model/concepts/concept-numeric.model';
import { ConceptNumericService } from './concept-numeric.service';

@Component({
  templateUrl: './concept-numeric-delete-dialog.component.html',
})
export class ConceptNumericDeleteDialogComponent {
  conceptNumeric?: IConceptNumeric;

  constructor(
    protected conceptNumericService: ConceptNumericService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptNumericService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conceptNumericListModification');
      this.activeModal.close();
    });
  }
}
