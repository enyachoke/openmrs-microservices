import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConceptReferenceTerm, ConceptReferenceTerm } from 'app/shared/model/concepts/concept-reference-term.model';
import { ConceptReferenceTermService } from './concept-reference-term.service';

@Component({
  selector: 'jhi-concept-reference-term-update',
  templateUrl: './concept-reference-term-update.component.html',
})
export class ConceptReferenceTermUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    name: [],
    code: [],
    version: [],
    description: [],
  });

  constructor(
    protected conceptReferenceTermService: ConceptReferenceTermService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptReferenceTerm }) => {
      this.updateForm(conceptReferenceTerm);
    });
  }

  updateForm(conceptReferenceTerm: IConceptReferenceTerm): void {
    this.editForm.patchValue({
      id: conceptReferenceTerm.id,
      uuid: conceptReferenceTerm.uuid,
      name: conceptReferenceTerm.name,
      code: conceptReferenceTerm.code,
      version: conceptReferenceTerm.version,
      description: conceptReferenceTerm.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conceptReferenceTerm = this.createFromForm();
    if (conceptReferenceTerm.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptReferenceTermService.update(conceptReferenceTerm));
    } else {
      this.subscribeToSaveResponse(this.conceptReferenceTermService.create(conceptReferenceTerm));
    }
  }

  private createFromForm(): IConceptReferenceTerm {
    return {
      ...new ConceptReferenceTerm(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      version: this.editForm.get(['version'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConceptReferenceTerm>>): void {
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
