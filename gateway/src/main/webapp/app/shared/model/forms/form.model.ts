export interface IForm {
  id?: number;
  uuid?: string;
  name?: string;
  version?: string;
  build?: number;
  published?: boolean;
  description?: string;
  encounterType?: string;
  template?: string;
  xslt?: string;
}

export class Form implements IForm {
  constructor(
    public id?: number,
    public uuid?: string,
    public name?: string,
    public version?: string,
    public build?: number,
    public published?: boolean,
    public description?: string,
    public encounterType?: string,
    public template?: string,
    public xslt?: string
  ) {
    this.published = this.published || false;
  }
}
