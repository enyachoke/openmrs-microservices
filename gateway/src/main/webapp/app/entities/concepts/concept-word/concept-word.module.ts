import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ConceptWordComponent } from './concept-word.component';
import { ConceptWordDetailComponent } from './concept-word-detail.component';
import { ConceptWordUpdateComponent } from './concept-word-update.component';
import { ConceptWordDeleteDialogComponent } from './concept-word-delete-dialog.component';
import { conceptWordRoute } from './concept-word.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(conceptWordRoute)],
  declarations: [ConceptWordComponent, ConceptWordDetailComponent, ConceptWordUpdateComponent, ConceptWordDeleteDialogComponent],
  entryComponents: [ConceptWordDeleteDialogComponent],
})
export class ConceptsConceptWordModule {}
