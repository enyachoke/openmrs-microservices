import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConceptName } from 'app/shared/model/concepts/concept-name.model';

type EntityResponseType = HttpResponse<IConceptName>;
type EntityArrayResponseType = HttpResponse<IConceptName[]>;

@Injectable({ providedIn: 'root' })
export class ConceptNameService {
  public resourceUrl = SERVER_API_URL + 'services/concepts/api/concept-names';

  constructor(protected http: HttpClient) {}

  create(conceptName: IConceptName): Observable<EntityResponseType> {
    return this.http.post<IConceptName>(this.resourceUrl, conceptName, { observe: 'response' });
  }

  update(conceptName: IConceptName): Observable<EntityResponseType> {
    return this.http.put<IConceptName>(this.resourceUrl, conceptName, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConceptName>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConceptName[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
