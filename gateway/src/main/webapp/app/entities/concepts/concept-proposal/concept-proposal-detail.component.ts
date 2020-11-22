import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConceptProposal } from 'app/shared/model/concepts/concept-proposal.model';

@Component({
  selector: 'jhi-concept-proposal-detail',
  templateUrl: './concept-proposal-detail.component.html',
})
export class ConceptProposalDetailComponent implements OnInit {
  conceptProposal: IConceptProposal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptProposal }) => (this.conceptProposal = conceptProposal));
  }

  previousState(): void {
    window.history.back();
  }
}
