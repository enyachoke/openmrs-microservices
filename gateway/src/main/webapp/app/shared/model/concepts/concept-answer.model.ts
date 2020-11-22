export interface IConceptAnswer {
  id?: number;
  uuid?: string;
  sortWeight?: number;
}

export class ConceptAnswer implements IConceptAnswer {
  constructor(public id?: number, public uuid?: string, public sortWeight?: number) {}
}
