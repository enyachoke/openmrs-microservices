import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFieldAnswer } from 'app/shared/model/forms/field-answer.model';

type EntityResponseType = HttpResponse<IFieldAnswer>;
type EntityArrayResponseType = HttpResponse<IFieldAnswer[]>;

@Injectable({ providedIn: 'root' })
export class FieldAnswerService {
  public resourceUrl = SERVER_API_URL + 'services/forms/api/field-answers';

  constructor(protected http: HttpClient) {}

  create(fieldAnswer: IFieldAnswer): Observable<EntityResponseType> {
    return this.http.post<IFieldAnswer>(this.resourceUrl, fieldAnswer, { observe: 'response' });
  }

  update(fieldAnswer: IFieldAnswer): Observable<EntityResponseType> {
    return this.http.put<IFieldAnswer>(this.resourceUrl, fieldAnswer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFieldAnswer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFieldAnswer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
