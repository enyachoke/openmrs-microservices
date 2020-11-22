import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrderType, OrderType } from 'app/shared/model/orders/order-type.model';
import { OrderTypeService } from './order-type.service';

@Component({
  selector: 'jhi-order-type-update',
  templateUrl: './order-type-update.component.html',
})
export class OrderTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    name: [],
    description: [],
  });

  constructor(protected orderTypeService: OrderTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderType }) => {
      this.updateForm(orderType);
    });
  }

  updateForm(orderType: IOrderType): void {
    this.editForm.patchValue({
      id: orderType.id,
      uuid: orderType.uuid,
      name: orderType.name,
      description: orderType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderType = this.createFromForm();
    if (orderType.id !== undefined) {
      this.subscribeToSaveResponse(this.orderTypeService.update(orderType));
    } else {
      this.subscribeToSaveResponse(this.orderTypeService.create(orderType));
    }
  }

  private createFromForm(): IOrderType {
    return {
      ...new OrderType(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderType>>): void {
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
