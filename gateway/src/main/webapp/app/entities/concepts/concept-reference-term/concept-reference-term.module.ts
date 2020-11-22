import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ConceptReferenceTermComponent } from './concept-reference-term.component';
import { ConceptReferenceTermDetailComponent } from './concept-reference-term-detail.component';
import { ConceptReferenceTermUpdateComponent } from './concept-reference-term-update.component';
import { ConceptReferenceTermDeleteDialogComponent } from './concept-reference-term-delete-dialog.component';
import { conceptReferenceTermRoute } from './concept-reference-term.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(conceptReferenceTermRoute)],
  declarations: [
    ConceptReferenceTermComponent,
    ConceptReferenceTermDetailComponent,
    ConceptReferenceTermUpdateComponent,
    ConceptReferenceTermDeleteDialogComponent,
  ],
  entryComponents: [ConceptReferenceTermDeleteDialogComponent],
})
export class ConceptsConceptReferenceTermModule {}
