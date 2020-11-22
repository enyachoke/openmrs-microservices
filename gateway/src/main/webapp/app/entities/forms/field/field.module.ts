import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { FieldComponent } from './field.component';
import { FieldDetailComponent } from './field-detail.component';
import { FieldUpdateComponent } from './field-update.component';
import { FieldDeleteDialogComponent } from './field-delete-dialog.component';
import { fieldRoute } from './field.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(fieldRoute)],
  declarations: [FieldComponent, FieldDetailComponent, FieldUpdateComponent, FieldDeleteDialogComponent],
  entryComponents: [FieldDeleteDialogComponent],
})
export class FormsFieldModule {}
