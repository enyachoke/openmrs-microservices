import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ConceptNameComponent } from './concept-name.component';
import { ConceptNameDetailComponent } from './concept-name-detail.component';
import { ConceptNameUpdateComponent } from './concept-name-update.component';
import { ConceptNameDeleteDialogComponent } from './concept-name-delete-dialog.component';
import { conceptNameRoute } from './concept-name.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(conceptNameRoute)],
  declarations: [ConceptNameComponent, ConceptNameDetailComponent, ConceptNameUpdateComponent, ConceptNameDeleteDialogComponent],
  entryComponents: [ConceptNameDeleteDialogComponent],
})
export class ConceptsConceptNameModule {}
