export interface IConcept {
  id?: number;
  uuid?: string;
  shortName?: string;
  description?: string;
  formText?: string;
  version?: string;
  isSet?: boolean;
}

export class Concept implements IConcept {
  constructor(
    public id?: number,
    public uuid?: string,
    public shortName?: string,
    public description?: string,
    public formText?: string,
    public version?: string,
    public isSet?: boolean
  ) {
    this.isSet = this.isSet || false;
  }
}
