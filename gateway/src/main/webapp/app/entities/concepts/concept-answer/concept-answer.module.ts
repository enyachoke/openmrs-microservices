import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ConceptAnswerComponent } from './concept-answer.component';
import { ConceptAnswerDetailComponent } from './concept-answer-detail.component';
import { ConceptAnswerUpdateComponent } from './concept-answer-update.component';
import { ConceptAnswerDeleteDialogComponent } from './concept-answer-delete-dialog.component';
import { conceptAnswerRoute } from './concept-answer.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(conceptAnswerRoute)],
  declarations: [ConceptAnswerComponent, ConceptAnswerDetailComponent, ConceptAnswerUpdateComponent, ConceptAnswerDeleteDialogComponent],
  entryComponents: [ConceptAnswerDeleteDialogComponent],
})
export class ConceptsConceptAnswerModule {}
