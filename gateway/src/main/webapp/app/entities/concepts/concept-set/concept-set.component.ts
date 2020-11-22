import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConceptSet } from 'app/shared/model/concepts/concept-set.model';
import { ConceptSetService } from './concept-set.service';
import { ConceptSetDeleteDialogComponent } from './concept-set-delete-dialog.component';

@Component({
  selector: 'jhi-concept-set',
  templateUrl: './concept-set.component.html',
})
export class ConceptSetComponent implements OnInit, OnDestroy {
  conceptSets?: IConceptSet[];
  eventSubscriber?: Subscription;

  constructor(protected conceptSetService: ConceptSetService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.conceptSetService.query().subscribe((res: HttpResponse<IConceptSet[]>) => (this.conceptSets = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConceptSets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConceptSet): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConceptSets(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptSetListModification', () => this.loadAll());
  }

  delete(conceptSet: IConceptSet): void {
    const modalRef = this.modalService.open(ConceptSetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conceptSet = conceptSet;
  }
}
