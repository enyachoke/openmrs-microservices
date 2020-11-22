import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConceptSet } from 'app/shared/model/concepts/concept-set.model';

type EntityResponseType = HttpResponse<IConceptSet>;
type EntityArrayResponseType = HttpResponse<IConceptSet[]>;

@Injectable({ providedIn: 'root' })
export class ConceptSetService {
  public resourceUrl = SERVER_API_URL + 'services/concepts/api/concept-sets';

  constructor(protected http: HttpClient) {}

  create(conceptSet: IConceptSet): Observable<EntityResponseType> {
    return this.http.post<IConceptSet>(this.resourceUrl, conceptSet, { observe: 'response' });
  }

  update(conceptSet: IConceptSet): Observable<EntityResponseType> {
    return this.http.put<IConceptSet>(this.resourceUrl, conceptSet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConceptSet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConceptSet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
