import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IForm } from 'app/shared/model/forms/form.model';

@Component({
  selector: 'jhi-form-detail',
  templateUrl: './form-detail.component.html',
})
export class FormDetailComponent implements OnInit {
  form: IForm | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ form }) => (this.form = form));
  }

  previousState(): void {
    window.history.back();
  }
}
