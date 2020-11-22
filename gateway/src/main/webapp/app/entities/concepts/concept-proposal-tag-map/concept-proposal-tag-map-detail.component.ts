import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptProposalTagMap } from 'app/shared/model/concepts/concept-proposal-tag-map.model';

@Component({
  selector: 'jhi-concept-proposal-tag-map-detail',
  templateUrl: './concept-proposal-tag-map-detail.component.html',
})
export class ConceptProposalTagMapDetailComponent implements OnInit {
  conceptProposalTagMap: IConceptProposalTagMap | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptProposalTagMap }) => (this.conceptProposalTagMap = conceptProposalTagMap));
  }

  previousState(): void {
    window.history.back();
  }
}
