import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConceptAnswer, ConceptAnswer } from 'app/shared/model/concepts/concept-answer.model';
import { ConceptAnswerService } from './concept-answer.service';

@Component({
  selector: 'jhi-concept-answer-update',
  templateUrl: './concept-answer-update.component.html',
})
export class ConceptAnswerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    sortWeight: [],
  });

  constructor(protected conceptAnswerService: ConceptAnswerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptAnswer }) => {
      this.updateForm(conceptAnswer);
    });
  }

  updateForm(conceptAnswer: IConceptAnswer): void {
    this.editForm.patchValue({
      id: conceptAnswer.id,
      uuid: conceptAnswer.uuid,
      sortWeight: conceptAnswer.sortWeight,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conceptAnswer = this.createFromForm();
    if (conceptAnswer.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptAnswerService.update(conceptAnswer));
    } else {
      this.subscribeToSaveResponse(this.conceptAnswerService.create(conceptAnswer));
    }
  }

  private createFromForm(): IConceptAnswer {
    return {
      ...new ConceptAnswer(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      sortWeight: this.editForm.get(['sortWeight'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConceptAnswer>>): void {
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
