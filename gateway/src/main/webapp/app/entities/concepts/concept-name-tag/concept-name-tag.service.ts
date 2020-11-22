import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConceptNameTag } from 'app/shared/model/concepts/concept-name-tag.model';

type EntityResponseType = HttpResponse<IConceptNameTag>;
type EntityArrayResponseType = HttpResponse<IConceptNameTag[]>;

@Injectable({ providedIn: 'root' })
export class ConceptNameTagService {
  public resourceUrl = SERVER_API_URL + 'services/concepts/api/concept-name-tags';

  constructor(protected http: HttpClient) {}

  create(conceptNameTag: IConceptNameTag): Observable<EntityResponseType> {
    return this.http.post<IConceptNameTag>(this.resourceUrl, conceptNameTag, { observe: 'response' });
  }

  update(conceptNameTag: IConceptNameTag): Observable<EntityResponseType> {
    return this.http.put<IConceptNameTag>(this.resourceUrl, conceptNameTag, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConceptNameTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConceptNameTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
