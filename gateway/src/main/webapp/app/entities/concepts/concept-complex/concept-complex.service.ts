import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConceptComplex } from 'app/shared/model/concepts/concept-complex.model';

type EntityResponseType = HttpResponse<IConceptComplex>;
type EntityArrayResponseType = HttpResponse<IConceptComplex[]>;

@Injectable({ providedIn: 'root' })
export class ConceptComplexService {
  public resourceUrl = SERVER_API_URL + 'services/concepts/api/concept-complexes';

  constructor(protected http: HttpClient) {}

  create(conceptComplex: IConceptComplex): Observable<EntityResponseType> {
    return this.http.post<IConceptComplex>(this.resourceUrl, conceptComplex, { observe: 'response' });
  }

  update(conceptComplex: IConceptComplex): Observable<EntityResponseType> {
    return this.http.put<IConceptComplex>(this.resourceUrl, conceptComplex, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConceptComplex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConceptComplex[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
