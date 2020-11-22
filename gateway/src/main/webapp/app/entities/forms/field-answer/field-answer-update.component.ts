import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFieldAnswer, FieldAnswer } from 'app/shared/model/forms/field-answer.model';
import { FieldAnswerService } from './field-answer.service';

@Component({
  selector: 'jhi-field-answer-update',
  templateUrl: './field-answer-update.component.html',
})
export class FieldAnswerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
  });

  constructor(protected fieldAnswerService: FieldAnswerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fieldAnswer }) => {
      this.updateForm(fieldAnswer);
    });
  }

  updateForm(fieldAnswer: IFieldAnswer): void {
    this.editForm.patchValue({
      id: fieldAnswer.id,
      uuid: fieldAnswer.uuid,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fieldAnswer = this.createFromForm();
    if (fieldAnswer.id !== undefined) {
      this.subscribeToSaveResponse(this.fieldAnswerService.update(fieldAnswer));
    } else {
      this.subscribeToSaveResponse(this.fieldAnswerService.create(fieldAnswer));
    }
  }

  private createFromForm(): IFieldAnswer {
    return {
      ...new FieldAnswer(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFieldAnswer>>): void {
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
