import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ConceptComponent } from './concept.component';
import { ConceptDetailComponent } from './concept-detail.component';
import { ConceptUpdateComponent } from './concept-update.component';
import { ConceptDeleteDialogComponent } from './concept-delete-dialog.component';
import { conceptRoute } from './concept.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(conceptRoute)],
  declarations: [ConceptComponent, ConceptDetailComponent, ConceptUpdateComponent, ConceptDeleteDialogComponent],
  entryComponents: [ConceptDeleteDialogComponent],
})
export class ConceptsConceptModule {}
