export interface IFieldType {
  id?: number;
  uuid?: string;
  name?: string;
  description?: string;
  isSet?: boolean;
}

export class FieldType implements IFieldType {
  constructor(public id?: number, public uuid?: string, public name?: string, public description?: string, public isSet?: boolean) {
    this.isSet = this.isSet || false;
  }
}
