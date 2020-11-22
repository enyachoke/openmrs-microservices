import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConceptAnswer } from 'app/shared/model/concepts/concept-answer.model';
import { ConceptAnswerService } from './concept-answer.service';
import { ConceptAnswerDeleteDialogComponent } from './concept-answer-delete-dialog.component';

@Component({
  selector: 'jhi-concept-answer',
  templateUrl: './concept-answer.component.html',
})
export class ConceptAnswerComponent implements OnInit, OnDestroy {
  conceptAnswers?: IConceptAnswer[];
  eventSubscriber?: Subscription;

  constructor(
    protected conceptAnswerService: ConceptAnswerService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conceptAnswerService.query().subscribe((res: HttpResponse<IConceptAnswer[]>) => (this.conceptAnswers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConceptAnswers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConceptAnswer): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConceptAnswers(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptAnswerListModification', () => this.loadAll());
  }

  delete(conceptAnswer: IConceptAnswer): void {
    const modalRef = this.modalService.open(ConceptAnswerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conceptAnswer = conceptAnswer;
  }
}
