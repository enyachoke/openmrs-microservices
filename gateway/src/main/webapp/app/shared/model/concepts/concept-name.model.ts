export interface IConceptName {
  id?: number;
  uuid?: string;
  name?: string;
  locale?: string;
  conceptNameType?: string;
}

export class ConceptName implements IConceptName {
  constructor(public id?: number, public uuid?: string, public name?: string, public locale?: string, public conceptNameType?: string) {}
}
