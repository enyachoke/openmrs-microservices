import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConceptNameTag, ConceptNameTag } from 'app/shared/model/concepts/concept-name-tag.model';
import { ConceptNameTagService } from './concept-name-tag.service';

@Component({
  selector: 'jhi-concept-name-tag-update',
  templateUrl: './concept-name-tag-update.component.html',
})
export class ConceptNameTagUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    tag: [],
    description: [],
  });

  constructor(protected conceptNameTagService: ConceptNameTagService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptNameTag }) => {
      this.updateForm(conceptNameTag);
    });
  }

  updateForm(conceptNameTag: IConceptNameTag): void {
    this.editForm.patchValue({
      id: conceptNameTag.id,
      uuid: conceptNameTag.uuid,
      tag: conceptNameTag.tag,
      description: conceptNameTag.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conceptNameTag = this.createFromForm();
    if (conceptNameTag.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptNameTagService.update(conceptNameTag));
    } else {
      this.subscribeToSaveResponse(this.conceptNameTagService.create(conceptNameTag));
    }
  }

  private createFromForm(): IConceptNameTag {
    return {
      ...new ConceptNameTag(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      tag: this.editForm.get(['tag'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConceptNameTag>>): void {
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
