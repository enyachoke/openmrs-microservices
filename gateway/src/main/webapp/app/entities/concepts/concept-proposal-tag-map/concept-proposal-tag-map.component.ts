import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConceptProposalTagMap } from 'app/shared/model/concepts/concept-proposal-tag-map.model';
import { ConceptProposalTagMapService } from './concept-proposal-tag-map.service';
import { ConceptProposalTagMapDeleteDialogComponent } from './concept-proposal-tag-map-delete-dialog.component';

@Component({
  selector: 'jhi-concept-proposal-tag-map',
  templateUrl: './concept-proposal-tag-map.component.html',
})
export class ConceptProposalTagMapComponent implements OnInit, OnDestroy {
  conceptProposalTagMaps?: IConceptProposalTagMap[];
  eventSubscriber?: Subscription;

  constructor(
    protected conceptProposalTagMapService: ConceptProposalTagMapService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conceptProposalTagMapService
      .query()
      .subscribe((res: HttpResponse<IConceptProposalTagMap[]>) => (this.conceptProposalTagMaps = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConceptProposalTagMaps();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConceptProposalTagMap): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConceptProposalTagMaps(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptProposalTagMapListModification', () => this.loadAll());
  }

  delete(conceptProposalTagMap: IConceptProposalTagMap): void {
    const modalRef = this.modalService.open(ConceptProposalTagMapDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conceptProposalTagMap = conceptProposalTagMap;
  }
}
