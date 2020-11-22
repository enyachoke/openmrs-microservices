import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ConceptDescriptionComponent } from './concept-description.component';
import { ConceptDescriptionDetailComponent } from './concept-description-detail.component';
import { ConceptDescriptionUpdateComponent } from './concept-description-update.component';
import { ConceptDescriptionDeleteDialogComponent } from './concept-description-delete-dialog.component';
import { conceptDescriptionRoute } from './concept-description.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(conceptDescriptionRoute)],
  declarations: [
    ConceptDescriptionComponent,
    ConceptDescriptionDetailComponent,
    ConceptDescriptionUpdateComponent,
    ConceptDescriptionDeleteDialogComponent,
  ],
  entryComponents: [ConceptDescriptionDeleteDialogComponent],
})
export class ConceptsConceptDescriptionModule {}
