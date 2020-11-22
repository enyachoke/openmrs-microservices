import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConcept, Concept } from 'app/shared/model/concepts/concept.model';
import { ConceptService } from './concept.service';

@Component({
  selector: 'jhi-concept-update',
  templateUrl: './concept-update.component.html',
})
export class ConceptUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    shortName: [],
    description: [],
    formText: [],
    version: [],
    isSet: [],
  });

  constructor(protected conceptService: ConceptService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concept }) => {
      this.updateForm(concept);
    });
  }

  updateForm(concept: IConcept): void {
    this.editForm.patchValue({
      id: concept.id,
      uuid: concept.uuid,
      shortName: concept.shortName,
      description: concept.description,
      formText: concept.formText,
      version: concept.version,
      isSet: concept.isSet,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const concept = this.createFromForm();
    if (concept.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptService.update(concept));
    } else {
      this.subscribeToSaveResponse(this.conceptService.create(concept));
    }
  }

  private createFromForm(): IConcept {
    return {
      ...new Concept(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      shortName: this.editForm.get(['shortName'])!.value,
      description: this.editForm.get(['description'])!.value,
      formText: this.editForm.get(['formText'])!.value,
      version: this.editForm.get(['version'])!.value,
      isSet: this.editForm.get(['isSet'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConcept>>): void {
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
