import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDrugOrder, DrugOrder } from 'app/shared/model/orders/drug-order.model';
import { DrugOrderService } from './drug-order.service';

@Component({
  selector: 'jhi-drug-order-update',
  templateUrl: './drug-order-update.component.html',
})
export class DrugOrderUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    drugInventoryUuid: [],
    dose: [],
    equivalentDailyDose: [],
    units: [],
    frequency: [],
    prn: [],
    complex: [],
    quantity: [],
  });

  constructor(protected drugOrderService: DrugOrderService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ drugOrder }) => {
      this.updateForm(drugOrder);
    });
  }

  updateForm(drugOrder: IDrugOrder): void {
    this.editForm.patchValue({
      id: drugOrder.id,
      uuid: drugOrder.uuid,
      drugInventoryUuid: drugOrder.drugInventoryUuid,
      dose: drugOrder.dose,
      equivalentDailyDose: drugOrder.equivalentDailyDose,
      units: drugOrder.units,
      frequency: drugOrder.frequency,
      prn: drugOrder.prn,
      complex: drugOrder.complex,
      quantity: drugOrder.quantity,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const drugOrder = this.createFromForm();
    if (drugOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.drugOrderService.update(drugOrder));
    } else {
      this.subscribeToSaveResponse(this.drugOrderService.create(drugOrder));
    }
  }

  private createFromForm(): IDrugOrder {
    return {
      ...new DrugOrder(),
      id: this.editForm.get(['id'])!.value,
      uuid: this.editForm.get(['uuid'])!.value,
      drugInventoryUuid: this.editForm.get(['drugInventoryUuid'])!.value,
      dose: this.editForm.get(['dose'])!.value,
      equivalentDailyDose: this.editForm.get(['equivalentDailyDose'])!.value,
      units: this.editForm.get(['units'])!.value,
      frequency: this.editForm.get(['frequency'])!.value,
      prn: this.editForm.get(['prn'])!.value,
      complex: this.editForm.get(['complex'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDrugOrder>>): void {
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
