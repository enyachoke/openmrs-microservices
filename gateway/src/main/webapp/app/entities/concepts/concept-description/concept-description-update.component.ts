import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConceptDescription, ConceptDescription } from 'app/shared/model/concepts/concept-description.model';
import { ConceptDescriptionService } from './concept-description.service';

@Component({
  selector: 'jhi-concept-description-update',
  templateUrl: './concept-description-update.component.html',
})
export class ConceptDescriptionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    description: [],
    locale: [],
  });

  constructor(
    protected conceptDescriptionService: ConceptDescriptionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptDescription }) => {
      this.updateForm(conceptDescription);
    });
  }

  updateForm(conceptDescription: IConceptDescription): void {
    this.editForm.patchValue({
      id: conceptDescription.id,
      uuid: conceptDescription.uuid,
      description: conceptDescription.description,
      locale: conceptDescription.locale,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conceptDescription = this.createFromForm();
    if (conceptDescription.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptDescriptionService.update(conceptDescription));
    } else {
      this.subscribeToSaveResponse(this.conceptDescriptionService.create(conceptDescription));
    }
  }

  private createFromForm(): IConceptDescription {
    return {
      ...new ConceptDescription(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      description: this.editForm.get(['description'])!.value,
      locale: this.editForm.get(['locale'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConceptDescription>>): void {
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
