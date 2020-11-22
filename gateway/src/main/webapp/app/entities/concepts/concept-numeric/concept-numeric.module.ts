import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ConceptNumericComponent } from './concept-numeric.component';
import { ConceptNumericDetailComponent } from './concept-numeric-detail.component';
import { ConceptNumericUpdateComponent } from './concept-numeric-update.component';
import { ConceptNumericDeleteDialogComponent } from './concept-numeric-delete-dialog.component';
import { conceptNumericRoute } from './concept-numeric.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(conceptNumericRoute)],
  declarations: [
    ConceptNumericComponent,
    ConceptNumericDetailComponent,
    ConceptNumericUpdateComponent,
    ConceptNumericDeleteDialogComponent,
  ],
  entryComponents: [ConceptNumericDeleteDialogComponent],
})
export class ConceptsConceptNumericModule {}
