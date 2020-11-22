import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConceptNumeric, ConceptNumeric } from 'app/shared/model/concepts/concept-numeric.model';
import { ConceptNumericService } from './concept-numeric.service';

@Component({
  selector: 'jhi-concept-numeric-update',
  templateUrl: './concept-numeric-update.component.html',
})
export class ConceptNumericUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    hiAbsolute: [],
    hiNormal: [],
    hiCritical: [],
    lowAbsolute: [],
    lowNormal: [],
    lowCritical: [],
    units: [],
    precise: [],
  });

  constructor(protected conceptNumericService: ConceptNumericService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptNumeric }) => {
      this.updateForm(conceptNumeric);
    });
  }

  updateForm(conceptNumeric: IConceptNumeric): void {
    this.editForm.patchValue({
      id: conceptNumeric.id,
      uuid: conceptNumeric.uuid,
      hiAbsolute: conceptNumeric.hiAbsolute,
      hiNormal: conceptNumeric.hiNormal,
      hiCritical: conceptNumeric.hiCritical,
      lowAbsolute: conceptNumeric.lowAbsolute,
      lowNormal: conceptNumeric.lowNormal,
      lowCritical: conceptNumeric.lowCritical,
      units: conceptNumeric.units,
      precise: conceptNumeric.precise,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conceptNumeric = this.createFromForm();
    if (conceptNumeric.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptNumericService.update(conceptNumeric));
    } else {
      this.subscribeToSaveResponse(this.conceptNumericService.create(conceptNumeric));
    }
  }

  private createFromForm(): IConceptNumeric {
    return {
      ...new ConceptNumeric(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      hiAbsolute: this.editForm.get(['hiAbsolute'])!.value,
      hiNormal: this.editForm.get(['hiNormal'])!.value,
      hiCritical: this.editForm.get(['hiCritical'])!.value,
      lowAbsolute: this.editForm.get(['lowAbsolute'])!.value,
      lowNormal: this.editForm.get(['lowNormal'])!.value,
      lowCritical: this.editForm.get(['lowCritical'])!.value,
      units: this.editForm.get(['units'])!.value,
      precise: this.editForm.get(['precise'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConceptNumeric>>): void {
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
