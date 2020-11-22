import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFieldAnswer } from 'app/shared/model/forms/field-answer.model';
import { FieldAnswerService } from './field-answer.service';
import { FieldAnswerDeleteDialogComponent } from './field-answer-delete-dialog.component';

@Component({
  selector: 'jhi-field-answer',
  templateUrl: './field-answer.component.html',
})
export class FieldAnswerComponent implements OnInit, OnDestroy {
  fieldAnswers?: IFieldAnswer[];
  eventSubscriber?: Subscription;

  constructor(
    protected fieldAnswerService: FieldAnswerService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.fieldAnswerService.query().subscribe((res: HttpResponse<IFieldAnswer[]>) => (this.fieldAnswers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFieldAnswers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFieldAnswer): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFieldAnswers(): void {
    this.eventSubscriber = this.eventManager.subscribe('fieldAnswerListModification', () => this.loadAll());
  }

  delete(fieldAnswer: IFieldAnswer): void {
    const modalRef = this.modalService.open(FieldAnswerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.fieldAnswer = fieldAnswer;
  }
}
