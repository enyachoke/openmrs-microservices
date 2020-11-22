export interface IConceptNumeric {
  id?: number;
  uuid?: string;
  hiAbsolute?: number;
  hiNormal?: number;
  hiCritical?: number;
  lowAbsolute?: number;
  lowNormal?: number;
  lowCritical?: number;
  units?: string;
  precise?: boolean;
}

export class ConceptNumeric implements IConceptNumeric {
  constructor(
    public id?: number,
    public uuid?: string,
    public hiAbsolute?: number,
    public hiNormal?: number,
    public hiCritical?: number,
    public lowAbsolute?: number,
    public lowNormal?: number,
    public lowCritical?: number,
    public units?: string,
    public precise?: boolean
  ) {
    this.precise = this.precise || false;
  }
}
