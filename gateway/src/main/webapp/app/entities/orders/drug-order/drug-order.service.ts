import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDrugOrder } from 'app/shared/model/orders/drug-order.model';

type EntityResponseType = HttpResponse<IDrugOrder>;
type EntityArrayResponseType = HttpResponse<IDrugOrder[]>;

@Injectable({ providedIn: 'root' })
export class DrugOrderService {
  public resourceUrl = SERVER_API_URL + 'services/orders/api/drug-orders';

  constructor(protected http: HttpClient) {}

  create(drugOrder: IDrugOrder): Observable<EntityResponseType> {
    return this.http.post<IDrugOrder>(this.resourceUrl, drugOrder, { observe: 'response' });
  }

  update(drugOrder: IDrugOrder): Observable<EntityResponseType> {
    return this.http.put<IDrugOrder>(this.resourceUrl, drugOrder, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDrugOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDrugOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
