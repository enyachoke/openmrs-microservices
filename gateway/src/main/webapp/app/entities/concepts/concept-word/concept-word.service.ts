import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConceptWord } from 'app/shared/model/concepts/concept-word.model';

type EntityResponseType = HttpResponse<IConceptWord>;
type EntityArrayResponseType = HttpResponse<IConceptWord[]>;

@Injectable({ providedIn: 'root' })
export class ConceptWordService {
  public resourceUrl = SERVER_API_URL + 'services/concepts/api/concept-words';

  constructor(protected http: HttpClient) {}

  create(conceptWord: IConceptWord): Observable<EntityResponseType> {
    return this.http.post<IConceptWord>(this.resourceUrl, conceptWord, { observe: 'response' });
  }

  update(conceptWord: IConceptWord): Observable<EntityResponseType> {
    return this.http.put<IConceptWord>(this.resourceUrl, conceptWord, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConceptWord>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConceptWord[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
