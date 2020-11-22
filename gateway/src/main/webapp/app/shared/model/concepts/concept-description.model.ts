export interface IConceptDescription {
  id?: number;
  uuid?: string;
  description?: string;
  locale?: string;
}

export class ConceptDescription implements IConceptDescription {
  constructor(public id?: number, public uuid?: string, public description?: string, public locale?: string) {}
}
