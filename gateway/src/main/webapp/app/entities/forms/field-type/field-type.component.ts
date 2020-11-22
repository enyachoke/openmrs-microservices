import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFieldType } from 'app/shared/model/forms/field-type.model';
import { FieldTypeService } from './field-type.service';
import { FieldTypeDeleteDialogComponent } from './field-type-delete-dialog.component';

@Component({
  selector: 'jhi-field-type',
  templateUrl: './field-type.component.html',
})
export class FieldTypeComponent implements OnInit, OnDestroy {
  fieldTypes?: IFieldType[];
  eventSubscriber?: Subscription;

  constructor(protected fieldTypeService: FieldTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.fieldTypeService.query().subscribe((res: HttpResponse<IFieldType[]>) => (this.fieldTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFieldTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFieldType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFieldTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('fieldTypeListModification', () => this.loadAll());
  }

  delete(fieldType: IFieldType): void {
    const modalRef = this.modalService.open(FieldTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.fieldType = fieldType;
  }
}
