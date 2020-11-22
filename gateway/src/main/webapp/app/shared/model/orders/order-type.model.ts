export interface IOrderType {
  id?: number;
  uuid?: string;
  name?: string;
  description?: string;
}

export class OrderType implements IOrderType {
  constructor(public id?: number, public uuid?: string, public name?: string, public description?: string) {}
}
