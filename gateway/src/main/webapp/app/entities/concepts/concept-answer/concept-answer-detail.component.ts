import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptAnswer } from 'app/shared/model/concepts/concept-answer.model';

@Component({
  selector: 'jhi-concept-answer-detail',
  templateUrl: './concept-answer-detail.component.html',
})
export class ConceptAnswerDetailComponent implements OnInit {
  conceptAnswer: IConceptAnswer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptAnswer }) => (this.conceptAnswer = conceptAnswer));
  }

  previousState(): void {
    window.history.back();
  }
}
