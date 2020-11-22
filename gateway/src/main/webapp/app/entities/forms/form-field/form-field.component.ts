import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFormField } from 'app/shared/model/forms/form-field.model';
import { FormFieldService } from './form-field.service';
import { FormFieldDeleteDialogComponent } from './form-field-delete-dialog.component';

@Component({
  selector: 'jhi-form-field',
  templateUrl: './form-field.component.html',
})
export class FormFieldComponent implements OnInit, OnDestroy {
  formFields?: IFormField[];
  eventSubscriber?: Subscription;

  constructor(protected formFieldService: FormFieldService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.formFieldService.query().subscribe((res: HttpResponse<IFormField[]>) => (this.formFields = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFormFields();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFormField): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFormFields(): void {
    this.eventSubscriber = this.eventManager.subscribe('formFieldListModification', () => this.loadAll());
  }

  delete(formField: IFormField): void {
    const modalRef = this.modalService.open(FormFieldDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.formField = formField;
  }
}
