import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConceptComplex } from 'app/shared/model/concepts/concept-complex.model';
import { ConceptComplexService } from './concept-complex.service';
import { ConceptComplexDeleteDialogComponent } from './concept-complex-delete-dialog.component';

@Component({
  selector: 'jhi-concept-complex',
  templateUrl: './concept-complex.component.html',
})
export class ConceptComplexComponent implements OnInit, OnDestroy {
  conceptComplexes?: IConceptComplex[];
  eventSubscriber?: Subscription;

  constructor(
    protected conceptComplexService: ConceptComplexService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conceptComplexService.query().subscribe((res: HttpResponse<IConceptComplex[]>) => (this.conceptComplexes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConceptComplexes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConceptComplex): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConceptComplexes(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptComplexListModification', () => this.loadAll());
  }

  delete(conceptComplex: IConceptComplex): void {
    const modalRef = this.modalService.open(ConceptComplexDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conceptComplex = conceptComplex;
  }
}
