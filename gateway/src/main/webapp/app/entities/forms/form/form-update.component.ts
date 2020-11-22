import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IForm, Form } from 'app/shared/model/forms/form.model';
import { FormService } from './form.service';

@Component({
  selector: 'jhi-form-update',
  templateUrl: './form-update.component.html',
})
export class FormUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    name: [],
    version: [],
    build: [],
    published: [],
    description: [],
    encounterType: [null, [Validators.required]],
    template: [],
    xslt: [],
  });

  constructor(protected formService: FormService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ form }) => {
      this.updateForm(form);
    });
  }

  updateForm(form: IForm): void {
    this.editForm.patchValue({
      id: form.id,
      uuid: form.uuid,
      name: form.name,
      version: form.version,
      build: form.build,
      published: form.published,
      description: form.description,
      encounterType: form.encounterType,
      template: form.template,
      xslt: form.xslt,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const form = this.createFromForm();
    if (form.id !== undefined) {
      this.subscribeToSaveResponse(this.formService.update(form));
    } else {
      this.subscribeToSaveResponse(this.formService.create(form));
    }
  }

  private createFromForm(): IForm {
    return {
      ...new Form(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      name: this.editForm.get(['name'])!.value,
      version: this.editForm.get(['version'])!.value,
      build: this.editForm.get(['build'])!.value,
      published: this.editForm.get(['published'])!.value,
      description: this.editForm.get(['description'])!.value,
      encounterType: this.editForm.get(['encounterType'])!.value,
      template: this.editForm.get(['template'])!.value,
      xslt: this.editForm.get(['xslt'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IForm>>): void {
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
