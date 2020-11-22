export interface IConceptNameTag {
  id?: number;
  uuid?: string;
  tag?: string;
  description?: string;
}

export class ConceptNameTag implements IConceptNameTag {
  constructor(public id?: number, public uuid?: string, public tag?: string, public description?: string) {}
}
