import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrder } from 'app/shared/model/orders/order.model';

type EntityResponseType = HttpResponse<IOrder>;
type EntityArrayResponseType = HttpResponse<IOrder[]>;

@Injectable({ providedIn: 'root' })
export class OrderService {
  public resourceUrl = SERVER_API_URL + 'services/orders/api/orders';

  constructor(protected http: HttpClient) {}

  create(order: IOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(order);
    return this.http
      .post<IOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(order: IOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(order);
    return this.http
      .put<IOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(order: IOrder): IOrder {
    const copy: IOrder = Object.assign({}, order, {
      startDate: order.startDate && order.startDate.isValid() ? order.startDate.format(DATE_FORMAT) : undefined,
      autoExpireDate: order.autoExpireDate && order.autoExpireDate.isValid() ? order.autoExpireDate.format(DATE_FORMAT) : undefined,
      discontinuedDate: order.discontinuedDate && order.discontinuedDate.isValid() ? order.discontinuedDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.autoExpireDate = res.body.autoExpireDate ? moment(res.body.autoExpireDate) : undefined;
      res.body.discontinuedDate = res.body.discontinuedDate ? moment(res.body.discontinuedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((order: IOrder) => {
        order.startDate = order.startDate ? moment(order.startDate) : undefined;
        order.autoExpireDate = order.autoExpireDate ? moment(order.autoExpireDate) : undefined;
        order.discontinuedDate = order.discontinuedDate ? moment(order.discontinuedDate) : undefined;
      });
    }
    return res;
  }
}
