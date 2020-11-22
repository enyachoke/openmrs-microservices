export interface IConceptWord {
  id?: number;
  uuid?: string;
  word?: string;
  locale?: string;
  weight?: number;
}

export class ConceptWord implements IConceptWord {
  constructor(public id?: number, public uuid?: string, public word?: string, public locale?: string, public weight?: number) {}
}
