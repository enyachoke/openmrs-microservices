import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConceptProposal } from 'app/shared/model/concepts/concept-proposal.model';
import { ConceptProposalService } from './concept-proposal.service';
import { ConceptProposalDeleteDialogComponent } from './concept-proposal-delete-dialog.component';

@Component({
  selector: 'jhi-concept-proposal',
  templateUrl: './concept-proposal.component.html',
})
export class ConceptProposalComponent implements OnInit, OnDestroy {
  conceptProposals?: IConceptProposal[];
  eventSubscriber?: Subscription;

  constructor(
    protected conceptProposalService: ConceptProposalService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conceptProposalService.query().subscribe((res: HttpResponse<IConceptProposal[]>) => (this.conceptProposals = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConceptProposals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConceptProposal): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConceptProposals(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptProposalListModification', () => this.loadAll());
  }

  delete(conceptProposal: IConceptProposal): void {
    const modalRef = this.modalService.open(ConceptProposalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conceptProposal = conceptProposal;
  }
}
