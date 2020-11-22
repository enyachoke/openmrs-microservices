import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConceptComplex, ConceptComplex } from 'app/shared/model/concepts/concept-complex.model';
import { ConceptComplexService } from './concept-complex.service';

@Component({
  selector: 'jhi-concept-complex-update',
  templateUrl: './concept-complex-update.component.html',
})
export class ConceptComplexUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    handler: [],
  });

  constructor(protected conceptComplexService: ConceptComplexService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptComplex }) => {
      this.updateForm(conceptComplex);
    });
  }

  updateForm(conceptComplex: IConceptComplex): void {
    this.editForm.patchValue({
      id: conceptComplex.id,
      uuid: conceptComplex.uuid,
      handler: conceptComplex.handler,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conceptComplex = this.createFromForm();
    if (conceptComplex.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptComplexService.update(conceptComplex));
    } else {
      this.subscribeToSaveResponse(this.conceptComplexService.create(conceptComplex));
    }
  }

  private createFromForm(): IConceptComplex {
    return {
      ...new ConceptComplex(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      handler: this.editForm.get(['handler'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConceptComplex>>): void {
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
