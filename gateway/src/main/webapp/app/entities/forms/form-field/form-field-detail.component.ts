import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormField } from 'app/shared/model/forms/form-field.model';

@Component({
  selector: 'jhi-form-field-detail',
  templateUrl: './form-field-detail.component.html',
})
export class FormFieldDetailComponent implements OnInit {
  formField: IFormField | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formField }) => (this.formField = formField));
  }

  previousState(): void {
    window.history.back();
  }
}
