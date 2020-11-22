import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConceptSet, ConceptSet } from 'app/shared/model/concepts/concept-set.model';
import { ConceptSetService } from './concept-set.service';

@Component({
  selector: 'jhi-concept-set-update',
  templateUrl: './concept-set-update.component.html',
})
export class ConceptSetUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    sortWeight: [],
  });

  constructor(protected conceptSetService: ConceptSetService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptSet }) => {
      this.updateForm(conceptSet);
    });
  }

  updateForm(conceptSet: IConceptSet): void {
    this.editForm.patchValue({
      id: conceptSet.id,
      uuid: conceptSet.uuid,
      sortWeight: conceptSet.sortWeight,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conceptSet = this.createFromForm();
    if (conceptSet.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptSetService.update(conceptSet));
    } else {
      this.subscribeToSaveResponse(this.conceptSetService.create(conceptSet));
    }
  }

  private createFromForm(): IConceptSet {
    return {
      ...new ConceptSet(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      sortWeight: this.editForm.get(['sortWeight'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConceptSet>>): void {
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
