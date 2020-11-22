import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConceptReferenceTerm } from 'app/shared/model/concepts/concept-reference-term.model';

type EntityResponseType = HttpResponse<IConceptReferenceTerm>;
type EntityArrayResponseType = HttpResponse<IConceptReferenceTerm[]>;

@Injectable({ providedIn: 'root' })
export class ConceptReferenceTermService {
  public resourceUrl = SERVER_API_URL + 'services/concepts/api/concept-reference-terms';

  constructor(protected http: HttpClient) {}

  create(conceptReferenceTerm: IConceptReferenceTerm): Observable<EntityResponseType> {
    return this.http.post<IConceptReferenceTerm>(this.resourceUrl, conceptReferenceTerm, { observe: 'response' });
  }

  update(conceptReferenceTerm: IConceptReferenceTerm): Observable<EntityResponseType> {
    return this.http.put<IConceptReferenceTerm>(this.resourceUrl, conceptReferenceTerm, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConceptReferenceTerm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConceptReferenceTerm[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
