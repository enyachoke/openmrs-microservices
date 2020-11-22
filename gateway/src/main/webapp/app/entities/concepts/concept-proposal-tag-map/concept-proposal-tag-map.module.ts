import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ConceptProposalTagMapComponent } from './concept-proposal-tag-map.component';
import { ConceptProposalTagMapDetailComponent } from './concept-proposal-tag-map-detail.component';
import { ConceptProposalTagMapUpdateComponent } from './concept-proposal-tag-map-update.component';
import { ConceptProposalTagMapDeleteDialogComponent } from './concept-proposal-tag-map-delete-dialog.component';
import { conceptProposalTagMapRoute } from './concept-proposal-tag-map.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(conceptProposalTagMapRoute)],
  declarations: [
    ConceptProposalTagMapComponent,
    ConceptProposalTagMapDetailComponent,
    ConceptProposalTagMapUpdateComponent,
    ConceptProposalTagMapDeleteDialogComponent,
  ],
  entryComponents: [ConceptProposalTagMapDeleteDialogComponent],
})
export class ConceptsConceptProposalTagMapModule {}
