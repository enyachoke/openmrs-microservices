import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConceptNumeric } from 'app/shared/model/concepts/concept-numeric.model';

type EntityResponseType = HttpResponse<IConceptNumeric>;
type EntityArrayResponseType = HttpResponse<IConceptNumeric[]>;

@Injectable({ providedIn: 'root' })
export class ConceptNumericService {
  public resourceUrl = SERVER_API_URL + 'services/concepts/api/concept-numerics';

  constructor(protected http: HttpClient) {}

  create(conceptNumeric: IConceptNumeric): Observable<EntityResponseType> {
    return this.http.post<IConceptNumeric>(this.resourceUrl, conceptNumeric, { observe: 'response' });
  }

  update(conceptNumeric: IConceptNumeric): Observable<EntityResponseType> {
    return this.http.put<IConceptNumeric>(this.resourceUrl, conceptNumeric, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConceptNumeric>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConceptNumeric[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
