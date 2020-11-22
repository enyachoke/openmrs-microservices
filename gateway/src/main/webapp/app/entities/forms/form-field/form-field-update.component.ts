import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFormField, FormField } from 'app/shared/model/forms/form-field.model';
import { FormFieldService } from './form-field.service';

@Component({
  selector: 'jhi-form-field-update',
  templateUrl: './form-field-update.component.html',
})
export class FormFieldUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    name: [],
    fieldNumber: [],
    fieldPart: [],
    pageNumber: [],
    minOccurs: [],
    maxOccurs: [],
    isRequired: [],
    sortWeight: [],
  });

  constructor(protected formFieldService: FormFieldService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formField }) => {
      this.updateForm(formField);
    });
  }

  updateForm(formField: IFormField): void {
    this.editForm.patchValue({
      id: formField.id,
      uuid: formField.uuid,
      name: formField.name,
      fieldNumber: formField.fieldNumber,
      fieldPart: formField.fieldPart,
      pageNumber: formField.pageNumber,
      minOccurs: formField.minOccurs,
      maxOccurs: formField.maxOccurs,
      isRequired: formField.isRequired,
      sortWeight: formField.sortWeight,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const formField = this.createFromForm();
    if (formField.id !== undefined) {
      this.subscribeToSaveResponse(this.formFieldService.update(formField));
    } else {
      this.subscribeToSaveResponse(this.formFieldService.create(formField));
    }
  }

  private createFromForm(): IFormField {
    return {
      ...new FormField(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      name: this.editForm.get(['name'])!.value,
      fieldNumber: this.editForm.get(['fieldNumber'])!.value,
      fieldPart: this.editForm.get(['fieldPart'])!.value,
      pageNumber: this.editForm.get(['pageNumber'])!.value,
      minOccurs: this.editForm.get(['minOccurs'])!.value,
      maxOccurs: this.editForm.get(['maxOccurs'])!.value,
      isRequired: this.editForm.get(['isRequired'])!.value,
      sortWeight: this.editForm.get(['sortWeight'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormField>>): void {
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
