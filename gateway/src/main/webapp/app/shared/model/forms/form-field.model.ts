export interface IFormField {
  id?: number;
  uuid?: string;
  name?: string;
  fieldNumber?: number;
  fieldPart?: string;
  pageNumber?: number;
  minOccurs?: number;
  maxOccurs?: number;
  isRequired?: boolean;
  sortWeight?: number;
}

export class FormField implements IFormField {
  constructor(
    public id?: number,
    public uuid?: string,
    public name?: string,
    public fieldNumber?: number,
    public fieldPart?: string,
    public pageNumber?: number,
    public minOccurs?: number,
    public maxOccurs?: number,
    public isRequired?: boolean,
    public sortWeight?: number
  ) {
    this.isRequired = this.isRequired || false;
  }
}
