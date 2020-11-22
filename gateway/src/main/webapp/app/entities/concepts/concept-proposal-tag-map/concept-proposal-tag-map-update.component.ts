import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConceptProposalTagMap, ConceptProposalTagMap } from 'app/shared/model/concepts/concept-proposal-tag-map.model';
import { ConceptProposalTagMapService } from './concept-proposal-tag-map.service';

@Component({
  selector: 'jhi-concept-proposal-tag-map-update',
  templateUrl: './concept-proposal-tag-map-update.component.html',
})
export class ConceptProposalTagMapUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
  });

  constructor(
    protected conceptProposalTagMapService: ConceptProposalTagMapService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptProposalTagMap }) => {
      this.updateForm(conceptProposalTagMap);
    });
  }

  updateForm(conceptProposalTagMap: IConceptProposalTagMap): void {
    this.editForm.patchValue({
      id: conceptProposalTagMap.id,
      uuid: conceptProposalTagMap.uuid,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conceptProposalTagMap = this.createFromForm();
    if (conceptProposalTagMap.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptProposalTagMapService.update(conceptProposalTagMap));
    } else {
      this.subscribeToSaveResponse(this.conceptProposalTagMapService.create(conceptProposalTagMap));
    }
  }

  private createFromForm(): IConceptProposalTagMap {
    return {
      ...new ConceptProposalTagMap(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConceptProposalTagMap>>): void {
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
