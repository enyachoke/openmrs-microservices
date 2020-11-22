import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptNameTagMap } from 'app/shared/model/concepts/concept-name-tag-map.model';
import { ConceptNameTagMapService } from './concept-name-tag-map.service';

@Component({
  templateUrl: './concept-name-tag-map-delete-dialog.component.html',
})
export class ConceptNameTagMapDeleteDialogComponent {
  conceptNameTagMap?: IConceptNameTagMap;

  constructor(
    protected conceptNameTagMapService: ConceptNameTagMapService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptNameTagMapService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conceptNameTagMapListModification');
      this.activeModal.close();
    });
  }
}
