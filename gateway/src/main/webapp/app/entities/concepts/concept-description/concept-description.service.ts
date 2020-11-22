import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConceptDescription } from 'app/shared/model/concepts/concept-description.model';

type EntityResponseType = HttpResponse<IConceptDescription>;
type EntityArrayResponseType = HttpResponse<IConceptDescription[]>;

@Injectable({ providedIn: 'root' })
export class ConceptDescriptionService {
  public resourceUrl = SERVER_API_URL + 'services/concepts/api/concept-descriptions';

  constructor(protected http: HttpClient) {}

  create(conceptDescription: IConceptDescription): Observable<EntityResponseType> {
    return this.http.post<IConceptDescription>(this.resourceUrl, conceptDescription, { observe: 'response' });
  }

  update(conceptDescription: IConceptDescription): Observable<EntityResponseType> {
    return this.http.put<IConceptDescription>(this.resourceUrl, conceptDescription, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConceptDescription>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConceptDescription[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
