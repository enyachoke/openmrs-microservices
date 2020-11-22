import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptSet } from 'app/shared/model/concepts/concept-set.model';

@Component({
  selector: 'jhi-concept-set-detail',
  templateUrl: './concept-set-detail.component.html',
})
export class ConceptSetDetailComponent implements OnInit {
  conceptSet: IConceptSet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptSet }) => (this.conceptSet = conceptSet));
  }

  previousState(): void {
    window.history.back();
  }
}
