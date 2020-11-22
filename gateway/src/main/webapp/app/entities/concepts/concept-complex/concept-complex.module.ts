import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ConceptComplexComponent } from './concept-complex.component';
import { ConceptComplexDetailComponent } from './concept-complex-detail.component';
import { ConceptComplexUpdateComponent } from './concept-complex-update.component';
import { ConceptComplexDeleteDialogComponent } from './concept-complex-delete-dialog.component';
import { conceptComplexRoute } from './concept-complex.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(conceptComplexRoute)],
  declarations: [
    ConceptComplexComponent,
    ConceptComplexDetailComponent,
    ConceptComplexUpdateComponent,
    ConceptComplexDeleteDialogComponent,
  ],
  entryComponents: [ConceptComplexDeleteDialogComponent],
})
export class ConceptsConceptComplexModule {}
