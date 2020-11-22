export interface IField {
  id?: number;
  uuid?: string;
  name?: string;
  description?: string;
  conceptUuid?: string;
  tableName?: string;
  attributesName?: string;
  defaultValue?: string;
  selectMultiple?: boolean;
}

export class Field implements IField {
  constructor(
    public id?: number,
    public uuid?: string,
    public name?: string,
    public description?: string,
    public conceptUuid?: string,
    public tableName?: string,
    public attributesName?: string,
    public defaultValue?: string,
    public selectMultiple?: boolean
  ) {
    this.selectMultiple = this.selectMultiple || false;
  }
}
