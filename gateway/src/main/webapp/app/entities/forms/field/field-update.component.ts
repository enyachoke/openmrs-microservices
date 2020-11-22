import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IField, Field } from 'app/shared/model/forms/field.model';
import { FieldService } from './field.service';

@Component({
  selector: 'jhi-field-update',
  templateUrl: './field-update.component.html',
})
export class FieldUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    name: [],
    description: [],
    conceptUuid: [],
    tableName: [],
    attributesName: [],
    defaultValue: [],
    selectMultiple: [],
  });

  constructor(protected fieldService: FieldService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ field }) => {
      this.updateForm(field);
    });
  }

  updateForm(field: IField): void {
    this.editForm.patchValue({
      id: field.id,
      uuid: field.uuid,
      name: field.name,
      description: field.description,
      conceptUuid: field.conceptUuid,
      tableName: field.tableName,
      attributesName: field.attributesName,
      defaultValue: field.defaultValue,
      selectMultiple: field.selectMultiple,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const field = this.createFromForm();
    if (field.id !== undefined) {
      this.subscribeToSaveResponse(this.fieldService.update(field));
    } else {
      this.subscribeToSaveResponse(this.fieldService.create(field));
    }
  }

  private createFromForm(): IField {
    return {
      ...new Field(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      conceptUuid: this.editForm.get(['conceptUuid'])!.value,
      tableName: this.editForm.get(['tableName'])!.value,
      attributesName: this.editForm.get(['attributesName'])!.value,
      defaultValue: this.editForm.get(['defaultValue'])!.value,
      selectMultiple: this.editForm.get(['selectMultiple'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IField>>): void {
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
