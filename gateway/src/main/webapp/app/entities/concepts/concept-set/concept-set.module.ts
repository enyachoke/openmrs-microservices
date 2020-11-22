import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ConceptSetComponent } from './concept-set.component';
import { ConceptSetDetailComponent } from './concept-set-detail.component';
import { ConceptSetUpdateComponent } from './concept-set-update.component';
import { ConceptSetDeleteDialogComponent } from './concept-set-delete-dialog.component';
import { conceptSetRoute } from './concept-set.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(conceptSetRoute)],
  declarations: [ConceptSetComponent, ConceptSetDetailComponent, ConceptSetUpdateComponent, ConceptSetDeleteDialogComponent],
  entryComponents: [ConceptSetDeleteDialogComponent],
})
export class ConceptsConceptSetModule {}
