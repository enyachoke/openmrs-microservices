import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConceptNameTagMap, ConceptNameTagMap } from 'app/shared/model/concepts/concept-name-tag-map.model';
import { ConceptNameTagMapService } from './concept-name-tag-map.service';

@Component({
  selector: 'jhi-concept-name-tag-map-update',
  templateUrl: './concept-name-tag-map-update.component.html',
})
export class ConceptNameTagMapUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
  });

  constructor(
    protected conceptNameTagMapService: ConceptNameTagMapService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptNameTagMap }) => {
      this.updateForm(conceptNameTagMap);
    });
  }

  updateForm(conceptNameTagMap: IConceptNameTagMap): void {
    this.editForm.patchValue({
      id: conceptNameTagMap.id,
      uuid: conceptNameTagMap.uuid,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conceptNameTagMap = this.createFromForm();
    if (conceptNameTagMap.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptNameTagMapService.update(conceptNameTagMap));
    } else {
      this.subscribeToSaveResponse(this.conceptNameTagMapService.create(conceptNameTagMap));
    }
  }

  private createFromForm(): IConceptNameTagMap {
    return {
      ...new ConceptNameTagMap(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConceptNameTagMap>>): void {
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
