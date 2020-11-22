import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptNameTagMap } from 'app/shared/model/concepts/concept-name-tag-map.model';

@Component({
  selector: 'jhi-concept-name-tag-map-detail',
  templateUrl: './concept-name-tag-map-detail.component.html',
})
export class ConceptNameTagMapDetailComponent implements OnInit {
  conceptNameTagMap: IConceptNameTagMap | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptNameTagMap }) => (this.conceptNameTagMap = conceptNameTagMap));
  }

  previousState(): void {
    window.history.back();
  }
}
