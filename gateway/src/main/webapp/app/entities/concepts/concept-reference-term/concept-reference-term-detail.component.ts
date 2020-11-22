import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptReferenceTerm } from 'app/shared/model/concepts/concept-reference-term.model';

@Component({
  selector: 'jhi-concept-reference-term-detail',
  templateUrl: './concept-reference-term-detail.component.html',
})
export class ConceptReferenceTermDetailComponent implements OnInit {
  conceptReferenceTerm: IConceptReferenceTerm | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptReferenceTerm }) => (this.conceptReferenceTerm = conceptReferenceTerm));
  }

  previousState(): void {
    window.history.back();
  }
}
