import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IField } from 'app/shared/model/forms/field.model';
import { FieldService } from './field.service';
import { FieldDeleteDialogComponent } from './field-delete-dialog.component';

@Component({
  selector: 'jhi-field',
  templateUrl: './field.component.html',
})
export class FieldComponent implements OnInit, OnDestroy {
  fields?: IField[];
  eventSubscriber?: Subscription;

  constructor(protected fieldService: FieldService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.fieldService.query().subscribe((res: HttpResponse<IField[]>) => (this.fields = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFields();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IField): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFields(): void {
    this.eventSubscriber = this.eventManager.subscribe('fieldListModification', () => this.loadAll());
  }

  delete(field: IField): void {
    const modalRef = this.modalService.open(FieldDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.field = field;
  }
}
