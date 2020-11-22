import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptWord } from 'app/shared/model/concepts/concept-word.model';

@Component({
  selector: 'jhi-concept-word-detail',
  templateUrl: './concept-word-detail.component.html',
})
export class ConceptWordDetailComponent implements OnInit {
  conceptWord: IConceptWord | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptWord }) => (this.conceptWord = conceptWord));
  }

  previousState(): void {
    window.history.back();
  }
}
