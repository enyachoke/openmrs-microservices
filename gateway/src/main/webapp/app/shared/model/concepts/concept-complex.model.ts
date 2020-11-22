export interface IConceptComplex {
  id?: number;
  uuid?: string;
  handler?: string;
}

export class ConceptComplex implements IConceptComplex {
  constructor(public id?: number, public uuid?: string, public handler?: string) {}
}
