import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFieldAnswer } from 'app/shared/model/forms/field-answer.model';

@Component({
  selector: 'jhi-field-answer-detail',
  templateUrl: './field-answer-detail.component.html',
})
export class FieldAnswerDetailComponent implements OnInit {
  fieldAnswer: IFieldAnswer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fieldAnswer }) => (this.fieldAnswer = fieldAnswer));
  }

  previousState(): void {
    window.history.back();
  }
}
