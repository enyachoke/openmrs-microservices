import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConceptNumeric } from 'app/shared/model/concepts/concept-numeric.model';
import { ConceptNumericService } from './concept-numeric.service';
import { ConceptNumericDeleteDialogComponent } from './concept-numeric-delete-dialog.component';

@Component({
  selector: 'jhi-concept-numeric',
  templateUrl: './concept-numeric.component.html',
})
export class ConceptNumericComponent implements OnInit, OnDestroy {
  conceptNumerics?: IConceptNumeric[];
  eventSubscriber?: Subscription;

  constructor(
    protected conceptNumericService: ConceptNumericService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conceptNumericService.query().subscribe((res: HttpResponse<IConceptNumeric[]>) => (this.conceptNumerics = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConceptNumerics();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConceptNumeric): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConceptNumerics(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptNumericListModification', () => this.loadAll());
  }

  delete(conceptNumeric: IConceptNumeric): void {
    const modalRef = this.modalService.open(ConceptNumericDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conceptNumeric = conceptNumeric;
  }
}
