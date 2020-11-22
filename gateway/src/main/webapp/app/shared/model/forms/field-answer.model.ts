export interface IFieldAnswer {
  id?: number;
  uuid?: string;
}

export class FieldAnswer implements IFieldAnswer {
  constructor(public id?: number, public uuid?: string) {}
}
