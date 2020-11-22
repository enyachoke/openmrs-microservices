import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptSet } from 'app/shared/model/concepts/concept-set.model';
import { ConceptSetService } from './concept-set.service';

@Component({
  templateUrl: './concept-set-delete-dialog.component.html',
})
export class ConceptSetDeleteDialogComponent {
  conceptSet?: IConceptSet;

  constructor(
    protected conceptSetService: ConceptSetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptSetService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conceptSetListModification');
      this.activeModal.close();
    });
  }
}
