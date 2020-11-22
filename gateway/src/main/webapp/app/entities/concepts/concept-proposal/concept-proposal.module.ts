import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { ConceptProposalComponent } from './concept-proposal.component';
import { ConceptProposalDetailComponent } from './concept-proposal-detail.component';
import { ConceptProposalUpdateComponent } from './concept-proposal-update.component';
import { ConceptProposalDeleteDialogComponent } from './concept-proposal-delete-dialog.component';
import { conceptProposalRoute } from './concept-proposal.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(conceptProposalRoute)],
  declarations: [
    ConceptProposalComponent,
    ConceptProposalDetailComponent,
    ConceptProposalUpdateComponent,
    ConceptProposalDeleteDialogComponent,
  ],
  entryComponents: [ConceptProposalDeleteDialogComponent],
})
export class ConceptsConceptProposalModule {}
