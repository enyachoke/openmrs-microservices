import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConceptNameTag } from 'app/shared/model/concepts/concept-name-tag.model';
import { ConceptNameTagService } from './concept-name-tag.service';
import { ConceptNameTagDeleteDialogComponent } from './concept-name-tag-delete-dialog.component';

@Component({
  selector: 'jhi-concept-name-tag',
  templateUrl: './concept-name-tag.component.html',
})
export class ConceptNameTagComponent implements OnInit, OnDestroy {
  conceptNameTags?: IConceptNameTag[];
  eventSubscriber?: Subscription;

  constructor(
    protected conceptNameTagService: ConceptNameTagService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conceptNameTagService.query().subscribe((res: HttpResponse<IConceptNameTag[]>) => (this.conceptNameTags = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConceptNameTags();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConceptNameTag): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConceptNameTags(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptNameTagListModification', () => this.loadAll());
  }

  delete(conceptNameTag: IConceptNameTag): void {
    const modalRef = this.modalService.open(ConceptNameTagDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conceptNameTag = conceptNameTag;
  }
}
