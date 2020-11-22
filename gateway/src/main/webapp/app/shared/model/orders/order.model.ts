import { Moment } from 'moment';

export interface IOrder {
  id?: number;
  uuid?: string;
  conceptUuid?: string;
  ordererUuid?: string;
  encounterUuid?: string;
  instructions?: string;
  startDate?: Moment;
  autoExpireDate?: Moment;
  discontinued?: boolean;
  discontinuedDate?: Moment;
  accessionNumber?: string;
  discontinuedReasonNonCoded?: string;
  patientUuid?: string;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public uuid?: string,
    public conceptUuid?: string,
    public ordererUuid?: string,
    public encounterUuid?: string,
    public instructions?: string,
    public startDate?: Moment,
    public autoExpireDate?: Moment,
    public discontinued?: boolean,
    public discontinuedDate?: Moment,
    public accessionNumber?: string,
    public discontinuedReasonNonCoded?: string,
    public patientUuid?: string
  ) {
    this.discontinued = this.discontinued || false;
  }
}
