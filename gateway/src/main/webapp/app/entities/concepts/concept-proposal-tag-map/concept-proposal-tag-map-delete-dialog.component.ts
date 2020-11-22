import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptProposalTagMap } from 'app/shared/model/concepts/concept-proposal-tag-map.model';
import { ConceptProposalTagMapService } from './concept-proposal-tag-map.service';

@Component({
  templateUrl: './concept-proposal-tag-map-delete-dialog.component.html',
})
export class ConceptProposalTagMapDeleteDialogComponent {
  conceptProposalTagMap?: IConceptProposalTagMap;

  constructor(
    protected conceptProposalTagMapService: ConceptProposalTagMapService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptProposalTagMapService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conceptProposalTagMapListModification');
      this.activeModal.close();
    });
  }
}
