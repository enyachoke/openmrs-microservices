import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptName } from 'app/shared/model/concepts/concept-name.model';
import { ConceptNameService } from './concept-name.service';

@Component({
  templateUrl: './concept-name-delete-dialog.component.html',
})
export class ConceptNameDeleteDialogComponent {
  conceptName?: IConceptName;

  constructor(
    protected conceptNameService: ConceptNameService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptNameService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conceptNameListModification');
      this.activeModal.close();
    });
  }
}
