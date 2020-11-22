import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFieldType } from 'app/shared/model/forms/field-type.model';

@Component({
  selector: 'jhi-field-type-detail',
  templateUrl: './field-type-detail.component.html',
})
export class FieldTypeDetailComponent implements OnInit {
  fieldType: IFieldType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fieldType }) => (this.fieldType = fieldType));
  }

  previousState(): void {
    window.history.back();
  }
}
