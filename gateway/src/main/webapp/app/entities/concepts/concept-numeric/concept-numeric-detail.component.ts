import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptNumeric } from 'app/shared/model/concepts/concept-numeric.model';

@Component({
  selector: 'jhi-concept-numeric-detail',
  templateUrl: './concept-numeric-detail.component.html',
})
export class ConceptNumericDetailComponent implements OnInit {
  conceptNumeric: IConceptNumeric | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptNumeric }) => (this.conceptNumeric = conceptNumeric));
  }

  previousState(): void {
    window.history.back();
  }
}
