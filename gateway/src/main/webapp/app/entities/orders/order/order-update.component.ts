import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrder, Order } from 'app/shared/model/orders/order.model';
import { OrderService } from './order.service';

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html',
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;
  startDateDp: any;
  autoExpireDateDp: any;
  discontinuedDateDp: any;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    conceptUuid: [],
    ordererUuid: [],
    encounterUuid: [],
    instructions: [],
    startDate: [],
    autoExpireDate: [],
    discontinued: [],
    discontinuedDate: [],
    accessionNumber: [],
    discontinuedReasonNonCoded: [],
    patientUuid: [],
  });

  constructor(protected orderService: OrderService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      this.updateForm(order);
    });
  }

  updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      uuid: order.uuid,
      conceptUuid: order.conceptUuid,
      ordererUuid: order.ordererUuid,
      encounterUuid: order.encounterUuid,
      instructions: order.instructions,
      startDate: order.startDate,
      autoExpireDate: order.autoExpireDate,
      discontinued: order.discontinued,
      discontinuedDate: order.discontinuedDate,
      accessionNumber: order.accessionNumber,
      discontinuedReasonNonCoded: order.discontinuedReasonNonCoded,
      patientUuid: order.patientUuid,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      conceptUuid: this.editForm.get(['conceptUuid'])!.value,
      ordererUuid: this.editForm.get(['ordererUuid'])!.value,
      encounterUuid: this.editForm.get(['encounterUuid'])!.value,
      instructions: this.editForm.get(['instructions'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      autoExpireDate: this.editForm.get(['autoExpireDate'])!.value,
      discontinued: this.editForm.get(['discontinued'])!.value,
      discontinuedDate: this.editForm.get(['discontinuedDate'])!.value,
      accessionNumber: this.editForm.get(['accessionNumber'])!.value,
      discontinuedReasonNonCoded: this.editForm.get(['discontinuedReasonNonCoded'])!.value,
      patientUuid: this.editForm.get(['patientUuid'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
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
