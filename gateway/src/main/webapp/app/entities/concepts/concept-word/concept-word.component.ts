import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConceptWord } from 'app/shared/model/concepts/concept-word.model';
import { ConceptWordService } from './concept-word.service';
import { ConceptWordDeleteDialogComponent } from './concept-word-delete-dialog.component';

@Component({
  selector: 'jhi-concept-word',
  templateUrl: './concept-word.component.html',
})
export class ConceptWordComponent implements OnInit, OnDestroy {
  conceptWords?: IConceptWord[];
  eventSubscriber?: Subscription;

  constructor(
    protected conceptWordService: ConceptWordService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conceptWordService.query().subscribe((res: HttpResponse<IConceptWord[]>) => (this.conceptWords = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConceptWords();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConceptWord): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConceptWords(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptWordListModification', () => this.loadAll());
  }

  delete(conceptWord: IConceptWord): void {
    const modalRef = this.modalService.open(ConceptWordDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conceptWord = conceptWord;
  }
}
