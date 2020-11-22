export interface IConceptNameTagMap {
  id?: number;
  uuid?: string;
}

export class ConceptNameTagMap implements IConceptNameTagMap {
  constructor(public id?: number, public uuid?: string) {}
}
