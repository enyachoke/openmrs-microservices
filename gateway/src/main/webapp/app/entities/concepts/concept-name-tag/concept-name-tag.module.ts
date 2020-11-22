import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ConceptNameTagComponent } from './concept-name-tag.component';
import { ConceptNameTagDetailComponent } from './concept-name-tag-detail.component';
import { ConceptNameTagUpdateComponent } from './concept-name-tag-update.component';
import { ConceptNameTagDeleteDialogComponent } from './concept-name-tag-delete-dialog.component';
import { conceptNameTagRoute } from './concept-name-tag.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(conceptNameTagRoute)],
  declarations: [
    ConceptNameTagComponent,
    ConceptNameTagDetailComponent,
    ConceptNameTagUpdateComponent,
    ConceptNameTagDeleteDialogComponent,
  ],
  entryComponents: [ConceptNameTagDeleteDialogComponent],
})
export class ConceptsConceptNameTagModule {}
