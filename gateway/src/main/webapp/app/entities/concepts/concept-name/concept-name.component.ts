import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConceptName } from 'app/shared/model/concepts/concept-name.model';
import { ConceptNameService } from './concept-name.service';
import { ConceptNameDeleteDialogComponent } from './concept-name-delete-dialog.component';

@Component({
  selector: 'jhi-concept-name',
  templateUrl: './concept-name.component.html',
})
export class ConceptNameComponent implements OnInit, OnDestroy {
  conceptNames?: IConceptName[];
  eventSubscriber?: Subscription;

  constructor(
    protected conceptNameService: ConceptNameService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conceptNameService.query().subscribe((res: HttpResponse<IConceptName[]>) => (this.conceptNames = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConceptNames();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConceptName): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConceptNames(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptNameListModification', () => this.loadAll());
  }

  delete(conceptName: IConceptName): void {
    const modalRef = this.modalService.open(ConceptNameDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conceptName = conceptName;
  }
}
