import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { FieldAnswerComponent } from './field-answer.component';
import { FieldAnswerDetailComponent } from './field-answer-detail.component';
import { FieldAnswerUpdateComponent } from './field-answer-update.component';
import { FieldAnswerDeleteDialogComponent } from './field-answer-delete-dialog.component';
import { fieldAnswerRoute } from './field-answer.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(fieldAnswerRoute)],
  declarations: [FieldAnswerComponent, FieldAnswerDetailComponent, FieldAnswerUpdateComponent, FieldAnswerDeleteDialogComponent],
  entryComponents: [FieldAnswerDeleteDialogComponent],
})
export class FormsFieldAnswerModule {}
