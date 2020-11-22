import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptNameTag } from 'app/shared/model/concepts/concept-name-tag.model';
import { ConceptNameTagService } from './concept-name-tag.service';

@Component({
  templateUrl: './concept-name-tag-delete-dialog.component.html',
})
export class ConceptNameTagDeleteDialogComponent {
  conceptNameTag?: IConceptNameTag;

  constructor(
    protected conceptNameTagService: ConceptNameTagService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptNameTagService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conceptNameTagListModification');
      this.activeModal.close();
    });
  }
}
