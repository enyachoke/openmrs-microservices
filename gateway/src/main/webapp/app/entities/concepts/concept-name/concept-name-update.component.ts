import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConceptName, ConceptName } from 'app/shared/model/concepts/concept-name.model';
import { ConceptNameService } from './concept-name.service';

@Component({
  selector: 'jhi-concept-name-update',
  templateUrl: './concept-name-update.component.html',
})
export class ConceptNameUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    name: [],
    locale: [],
    conceptNameType: [],
  });

  constructor(protected conceptNameService: ConceptNameService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptName }) => {
      this.updateForm(conceptName);
    });
  }

  updateForm(conceptName: IConceptName): void {
    this.editForm.patchValue({
      id: conceptName.id,
      uuid: conceptName.uuid,
      name: conceptName.name,
      locale: conceptName.locale,
      conceptNameType: conceptName.conceptNameType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conceptName = this.createFromForm();
    if (conceptName.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptNameService.update(conceptName));
    } else {
      this.subscribeToSaveResponse(this.conceptNameService.create(conceptName));
    }
  }

  private createFromForm(): IConceptName {
    return {
      ...new ConceptName(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      name: this.editForm.get(['name'])!.value,
      locale: this.editForm.get(['locale'])!.value,
      conceptNameType: this.editForm.get(['conceptNameType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConceptName>>): void {
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
