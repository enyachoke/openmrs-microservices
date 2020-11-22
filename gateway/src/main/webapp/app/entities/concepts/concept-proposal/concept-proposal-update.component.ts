import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConceptProposal, ConceptProposal } from 'app/shared/model/concepts/concept-proposal.model';
import { ConceptProposalService } from './concept-proposal.service';

@Component({
  selector: 'jhi-concept-proposal-update',
  templateUrl: './concept-proposal-update.component.html',
})
export class ConceptProposalUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    encounter: [],
    originalText: [],
    finalText: [],
    obsUuid: [],
    obsConceptUuid: [],
    state: [],
    comments: [],
    locale: [],
  });

  constructor(
    protected conceptProposalService: ConceptProposalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptProposal }) => {
      this.updateForm(conceptProposal);
    });
  }

  updateForm(conceptProposal: IConceptProposal): void {
    this.editForm.patchValue({
      id: conceptProposal.id,
      uuid: conceptProposal.uuid,
      encounter: conceptProposal.encounter,
      originalText: conceptProposal.originalText,
      finalText: conceptProposal.finalText,
      obsUuid: conceptProposal.obsUuid,
      obsConceptUuid: conceptProposal.obsConceptUuid,
      state: conceptProposal.state,
      comments: conceptProposal.comments,
      locale: conceptProposal.locale,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conceptProposal = this.createFromForm();
    if (conceptProposal.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptProposalService.update(conceptProposal));
    } else {
      this.subscribeToSaveResponse(this.conceptProposalService.create(conceptProposal));
    }
  }

  private createFromForm(): IConceptProposal {
    return {
      ...new ConceptProposal(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      encounter: this.editForm.get(['encounter'])!.value,
      originalText: this.editForm.get(['originalText'])!.value,
      finalText: this.editForm.get(['finalText'])!.value,
      obsUuid: this.editForm.get(['obsUuid'])!.value,
      obsConceptUuid: this.editForm.get(['obsConceptUuid'])!.value,
      state: this.editForm.get(['state'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      locale: this.editForm.get(['locale'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConceptProposal>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
