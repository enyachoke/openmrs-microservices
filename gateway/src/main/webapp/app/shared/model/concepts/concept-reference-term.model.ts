export interface IConceptReferenceTerm {
  id?: number;
  uuid?: string;
  name?: string;
  code?: string;
  version?: string;
  description?: string;
}

export class ConceptReferenceTerm implements IConceptReferenceTerm {
  constructor(
    public id?: number,
    public uuid?: string,
    public name?: string,
    public code?: string,
    public version?: string,
    public description?: string
  ) {}
}
