import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptName } from 'app/shared/model/concepts/concept-name.model';

@Component({
  selector: 'jhi-concept-name-detail',
  templateUrl: './concept-name-detail.component.html',
})
export class ConceptNameDetailComponent implements OnInit {
  conceptName: IConceptName | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptName }) => (this.conceptName = conceptName));
  }

  previousState(): void {
    window.history.back();
  }
}
