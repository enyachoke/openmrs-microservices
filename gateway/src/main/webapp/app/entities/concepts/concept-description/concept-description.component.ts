import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConceptDescription } from 'app/shared/model/concepts/concept-description.model';
import { ConceptDescriptionService } from './concept-description.service';
import { ConceptDescriptionDeleteDialogComponent } from './concept-description-delete-dialog.component';

@Component({
  selector: 'jhi-concept-description',
  templateUrl: './concept-description.component.html',
})
export class ConceptDescriptionComponent implements OnInit, OnDestroy {
  conceptDescriptions?: IConceptDescription[];
  eventSubscriber?: Subscription;

  constructor(
    protected conceptDescriptionService: ConceptDescriptionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conceptDescriptionService
      .query()
      .subscribe((res: HttpResponse<IConceptDescription[]>) => (this.conceptDescriptions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConceptDescriptions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConceptDescription): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConceptDescriptions(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptDescriptionListModification', () => this.loadAll());
  }

  delete(conceptDescription: IConceptDescription): void {
    const modalRef = this.modalService.open(ConceptDescriptionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conceptDescription = conceptDescription;
  }
}
