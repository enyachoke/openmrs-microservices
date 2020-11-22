export interface IDrugOrder {
  id?: number;
  uuid?: string;
  drugInventoryUuid?: string;
  dose?: number;
  equivalentDailyDose?: number;
  units?: string;
  frequency?: string;
  prn?: boolean;
  complex?: boolean;
  quantity?: number;
}

export class DrugOrder implements IDrugOrder {
  constructor(
    public id?: number,
    public uuid?: string,
    public drugInventoryUuid?: string,
    public dose?: number,
    public equivalentDailyDose?: number,
    public units?: string,
    public frequency?: string,
    public prn?: boolean,
    public complex?: boolean,
    public quantity?: number
  ) {
    this.prn = this.prn || false;
    this.complex = this.complex || false;
  }
}
