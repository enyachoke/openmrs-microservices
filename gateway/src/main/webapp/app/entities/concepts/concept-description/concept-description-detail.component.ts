import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptDescription } from 'app/shared/model/concepts/concept-description.model';

@Component({
  selector: 'jhi-concept-description-detail',
  templateUrl: './concept-description-detail.component.html',
})
export class ConceptDescriptionDetailComponent implements OnInit {
  conceptDescription: IConceptDescription | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptDescription }) => (this.conceptDescription = conceptDescription));
  }

  previousState(): void {
    window.history.back();
  }
}
