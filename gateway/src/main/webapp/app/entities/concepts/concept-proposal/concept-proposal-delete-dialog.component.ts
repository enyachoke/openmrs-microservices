import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConceptProposal } from 'app/shared/model/concepts/concept-proposal.model';
import { ConceptProposalService } from './concept-proposal.service';

@Component({
  templateUrl: './concept-proposal-delete-dialog.component.html',
})
export class ConceptProposalDeleteDialogComponent {
  conceptProposal?: IConceptProposal;

  constructor(
    protected conceptProposalService: ConceptProposalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conceptProposalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conceptProposalListModification');
      this.activeModal.close();
    });
  }
}
