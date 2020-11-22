import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ConceptNameTagMapComponent } from './concept-name-tag-map.component';
import { ConceptNameTagMapDetailComponent } from './concept-name-tag-map-detail.component';
import { ConceptNameTagMapUpdateComponent } from './concept-name-tag-map-update.component';
import { ConceptNameTagMapDeleteDialogComponent } from './concept-name-tag-map-delete-dialog.component';
import { conceptNameTagMapRoute } from './concept-name-tag-map.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(conceptNameTagMapRoute)],
  declarations: [
    ConceptNameTagMapComponent,
    ConceptNameTagMapDetailComponent,
    ConceptNameTagMapUpdateComponent,
    ConceptNameTagMapDeleteDialogComponent,
  ],
  entryComponents: [ConceptNameTagMapDeleteDialogComponent],
})
export class ConceptsConceptNameTagMapModule {}
