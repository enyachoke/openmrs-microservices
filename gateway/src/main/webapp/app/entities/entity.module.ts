import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'form',
        loadChildren: () => import('./forms/form/form.module').then(m => m.FormsFormModule),
      },
      {
        path: 'form-field',
        loadChildren: () => import('./forms/form-field/form-field.module').then(m => m.FormsFormFieldModule),
      },
      {
        path: 'field-type',
        loadChildren: () => import('./forms/field-type/field-type.module').then(m => m.FormsFieldTypeModule),
      },
      {
        path: 'field-answer',
        loadChildren: () => import('./forms/field-answer/field-answer.module').then(m => m.FormsFieldAnswerModule),
      },
      {
        path: 'field',
        loadChildren: () => import('./forms/field/field.module').then(m => m.FormsFieldModule),
      },
      {
        path: 'order',
        loadChildren: () => import('./orders/order/order.module').then(m => m.OrdersOrderModule),
      },
      {
        path: 'order-type',
        loadChildren: () => import('./orders/order-type/order-type.module').then(m => m.OrdersOrderTypeModule),
      },
      {
        path: 'drug-order',
        loadChildren: () => import('./orders/drug-order/drug-order.module').then(m => m.OrdersDrugOrderModule),
      },
      {
        path: 'concept',
        loadChildren: () => import('./concepts/concept/concept.module').then(m => m.ConceptsConceptModule),
      },
      {
        path: 'concept-name',
        loadChildren: () => import('./concepts/concept-name/concept-name.module').then(m => m.ConceptsConceptNameModule),
      },
      {
        path: 'concept-description',
        loadChildren: () =>
          import('./concepts/concept-description/concept-description.module').then(m => m.ConceptsConceptDescriptionModule),
      },
      {
        path: 'concept-set',
        loadChildren: () => import('./concepts/concept-set/concept-set.module').then(m => m.ConceptsConceptSetModule),
      },
      {
        path: 'concept-numeric',
        loadChildren: () => import('./concepts/concept-numeric/concept-numeric.module').then(m => m.ConceptsConceptNumericModule),
      },
      {
        path: 'concept-complex',
        loadChildren: () => import('./concepts/concept-complex/concept-complex.module').then(m => m.ConceptsConceptComplexModule),
      },
      {
        path: 'concept-answer',
        loadChildren: () => import('./concepts/concept-answer/concept-answer.module').then(m => m.ConceptsConceptAnswerModule),
      },
      {
        path: 'concept-name-tag',
        loadChildren: () => import('./concepts/concept-name-tag/concept-name-tag.module').then(m => m.ConceptsConceptNameTagModule),
      },
      {
        path: 'concept-word',
        loadChildren: () => import('./concepts/concept-word/concept-word.module').then(m => m.ConceptsConceptWordModule),
      },
      {
        path: 'concept-proposal-tag-map',
        loadChildren: () =>
          import('./concepts/concept-proposal-tag-map/concept-proposal-tag-map.module').then(m => m.ConceptsConceptProposalTagMapModule),
      },
      {
        path: 'concept-name-tag-map',
        loadChildren: () =>
          import('./concepts/concept-name-tag-map/concept-name-tag-map.module').then(m => m.ConceptsConceptNameTagMapModule),
      },
      {
        path: 'concept-proposal',
        loadChildren: () => import('./concepts/concept-proposal/concept-proposal.module').then(m => m.ConceptsConceptProposalModule),
      },
      {
        path: 'concept-reference-term',
        loadChildren: () =>
          import('./concepts/concept-reference-term/concept-reference-term.module').then(m => m.ConceptsConceptReferenceTermModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GatewayEntityModule {}
