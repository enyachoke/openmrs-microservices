import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConceptWord, ConceptWord } from 'app/shared/model/concepts/concept-word.model';
import { ConceptWordService } from './concept-word.service';

@Component({
  selector: 'jhi-concept-word-update',
  templateUrl: './concept-word-update.component.html',
})
export class ConceptWordUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    word: [],
    locale: [],
    weight: [],
  });

  constructor(protected conceptWordService: ConceptWordService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conceptWord }) => {
      this.updateForm(conceptWord);
    });
  }

  updateForm(conceptWord: IConceptWord): void {
    this.editForm.patchValue({
      id: conceptWord.id,
      uuid: conceptWord.uuid,
      word: conceptWord.word,
      locale: conceptWord.locale,
      weight: conceptWord.weight,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conceptWord = this.createFromForm();
    if (conceptWord.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptWordService.update(conceptWord));
    } else {
      this.subscribeToSaveResponse(this.conceptWordService.create(conceptWord));
    }
  }

  private createFromForm(): IConceptWord {
    return {
      ...new ConceptWord(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      word: this.editForm.get(['word'])!.value,
      locale: this.editForm.get(['locale'])!.value,
      weight: this.editForm.get(['weight'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConceptWord>>): void {
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
