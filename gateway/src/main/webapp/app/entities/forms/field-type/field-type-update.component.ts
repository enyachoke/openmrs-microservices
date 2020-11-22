import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFieldType, FieldType } from 'app/shared/model/forms/field-type.model';
import { FieldTypeService } from './field-type.service';

@Component({
  selector: 'jhi-field-type-update',
  templateUrl: './field-type-update.component.html',
})
export class FieldTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    name: [],
    description: [],
    isSet: [],
  });

  constructor(protected fieldTypeService: FieldTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fieldType }) => {
      this.updateForm(fieldType);
    });
  }

  updateForm(fieldType: IFieldType): void {
    this.editForm.patchValue({
      id: fieldType.id,
      uuid: fieldType.uuid,
      name: fieldType.name,
      description: fieldType.description,
      isSet: fieldType.isSet,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fieldType = this.createFromForm();
    if (fieldType.id !== undefined) {
      this.subscribeToSaveResponse(this.fieldTypeService.update(fieldType));
    } else {
      this.subscribeToSaveResponse(this.fieldTypeService.create(fieldType));
    }
  }

  private createFromForm(): IFieldType {
    return {
      ...new FieldType(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      isSet: this.editForm.get(['isSet'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFieldType>>): void {
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
