export interface IConceptSet {
  id?: number;
  uuid?: string;
  sortWeight?: number;
}

export class ConceptSet implements IConceptSet {
  constructor(public id?: number, public uuid?: string, public sortWeight?: number) {}
}
