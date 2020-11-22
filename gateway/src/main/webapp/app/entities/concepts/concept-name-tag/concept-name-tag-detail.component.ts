import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptNameTag } from 'app/shared/model/concepts/concept-name-tag.model';

@Component({
  selector: 'jhi-concept-name-tag-detail',
  templateUrl: './concept-name-tag-detail.component.html',
})
export class ConceptNameTagDetailComponent implements OnInit {
  conceptNameTag: IConceptNameTag | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptNameTag }) => (this.conceptNameTag = conceptNameTag));
  }

  previousState(): void {
    window.history.back();
  }
}
