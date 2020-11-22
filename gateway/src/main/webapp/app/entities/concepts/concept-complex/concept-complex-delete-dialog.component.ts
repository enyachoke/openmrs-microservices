import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptComplex } from 'app/shared/model/concepts/concept-complex.model';
import { ConceptComplexService } from './concept-complex.service';

@Component({
  templateUrl: './concept-complex-delete-dialog.component.html',
})
export class ConceptComplexDeleteDialogComponent {
  conceptComplex?: IConceptComplex;

  constructor(
    protected conceptComplexService: ConceptComplexService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptComplexService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conceptComplexListModification');
      this.activeModal.close();
    });
  }
}
