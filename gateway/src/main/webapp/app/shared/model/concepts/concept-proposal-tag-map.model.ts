export interface IConceptProposalTagMap {
  id?: number;
  uuid?: string;
}

export class ConceptProposalTagMap implements IConceptProposalTagMap {
  constructor(public id?: number, public uuid?: string) {}
}
