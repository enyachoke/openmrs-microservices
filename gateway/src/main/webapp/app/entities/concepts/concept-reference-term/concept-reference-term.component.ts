import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConceptReferenceTerm } from 'app/shared/model/concepts/concept-reference-term.model';
import { ConceptReferenceTermService } from './concept-reference-term.service';
import { ConceptReferenceTermDeleteDialogComponent } from './concept-reference-term-delete-dialog.component';

@Component({
  selector: 'jhi-concept-reference-term',
  templateUrl: './concept-reference-term.component.html',
})
export class ConceptReferenceTermComponent implements OnInit, OnDestroy {
  conceptReferenceTerms?: IConceptReferenceTerm[];
  eventSubscriber?: Subscription;

  constructor(
    protected conceptReferenceTermService: ConceptReferenceTermService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conceptReferenceTermService
      .query()
      .subscribe((res: HttpResponse<IConceptReferenceTerm[]>) => (this.conceptReferenceTerms = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConceptReferenceTerms();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConceptReferenceTerm): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConceptReferenceTerms(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptReferenceTermListModification', () => this.loadAll());
  }

  delete(conceptReferenceTerm: IConceptReferenceTerm): void {
    const modalRef = this.modalService.open(ConceptReferenceTermDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conceptReferenceTerm = conceptReferenceTerm;
  }
}
