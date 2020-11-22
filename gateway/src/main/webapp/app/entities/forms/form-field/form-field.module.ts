import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { FormFieldComponent } from './form-field.component';
import { FormFieldDetailComponent } from './form-field-detail.component';
import { FormFieldUpdateComponent } from './form-field-update.component';
import { FormFieldDeleteDialogComponent } from './form-field-delete-dialog.component';
import { formFieldRoute } from './form-field.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(formFieldRoute)],
  declarations: [FormFieldComponent, FormFieldDetailComponent, FormFieldUpdateComponent, FormFieldDeleteDialogComponent],
  entryComponents: [FormFieldDeleteDialogComponent],
})
export class FormsFormFieldModule {}
