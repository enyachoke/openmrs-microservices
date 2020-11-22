import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptComplex } from 'app/shared/model/concepts/concept-complex.model';

@Component({
  selector: 'jhi-concept-complex-detail',
  templateUrl: './concept-complex-detail.component.html',
})
export class ConceptComplexDetailComponent implements OnInit {
  conceptComplex: IConceptComplex | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptComplex }) => (this.conceptComplex = conceptComplex));
  }

  previousState(): void {
    window.history.back();
  }
}
