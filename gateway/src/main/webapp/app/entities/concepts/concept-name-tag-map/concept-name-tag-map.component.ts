import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConceptNameTagMap } from 'app/shared/model/concepts/concept-name-tag-map.model';
import { ConceptNameTagMapService } from './concept-name-tag-map.service';
import { ConceptNameTagMapDeleteDialogComponent } from './concept-name-tag-map-delete-dialog.component';

@Component({
  selector: 'jhi-concept-name-tag-map',
  templateUrl: './concept-name-tag-map.component.html',
})
export class ConceptNameTagMapComponent implements OnInit, OnDestroy {
  conceptNameTagMaps?: IConceptNameTagMap[];
  eventSubscriber?: Subscription;

  constructor(
    protected conceptNameTagMapService: ConceptNameTagMapService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conceptNameTagMapService
      .query()
      .subscribe((res: HttpResponse<IConceptNameTagMap[]>) => (this.conceptNameTagMaps = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConceptNameTagMaps();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConceptNameTagMap): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConceptNameTagMaps(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptNameTagMapListModification', () => this.loadAll());
  }

  delete(conceptNameTagMap: IConceptNameTagMap): void {
    const modalRef = this.modalService.open(ConceptNameTagMapDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conceptNameTagMap = conceptNameTagMap;
  }
}
