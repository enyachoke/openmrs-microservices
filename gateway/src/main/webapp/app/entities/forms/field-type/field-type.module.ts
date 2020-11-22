import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { FieldTypeComponent } from './field-type.component';
import { FieldTypeDetailComponent } from './field-type-detail.component';
import { FieldTypeUpdateComponent } from './field-type-update.component';
import { FieldTypeDeleteDialogComponent } from './field-type-delete-dialog.component';
import { fieldTypeRoute } from './field-type.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(fieldTypeRoute)],
  declarations: [FieldTypeComponent, FieldTypeDetailComponent, FieldTypeUpdateComponent, FieldTypeDeleteDialogComponent],
  entryComponents: [FieldTypeDeleteDialogComponent],
})
export class FormsFieldTypeModule {}
