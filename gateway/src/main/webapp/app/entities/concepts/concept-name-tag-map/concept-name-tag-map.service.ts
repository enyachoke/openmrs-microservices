import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConceptNameTagMap } from 'app/shared/model/concepts/concept-name-tag-map.model';

type EntityResponseType = HttpResponse<IConceptNameTagMap>;
type EntityArrayResponseType = HttpResponse<IConceptNameTagMap[]>;

@Injectable({ providedIn: 'root' })
export class ConceptNameTagMapService {
  public resourceUrl = SERVER_API_URL + 'services/concepts/api/concept-name-tag-maps';

  constructor(protected http: HttpClient) {}

  create(conceptNameTagMap: IConceptNameTagMap): Observable<EntityResponseType> {
    return this.http.post<IConceptNameTagMap>(this.resourceUrl, conceptNameTagMap, { observe: 'response' });
  }

  update(conceptNameTagMap: IConceptNameTagMap): Observable<EntityResponseType> {
    return this.http.put<IConceptNameTagMap>(this.resourceUrl, conceptNameTagMap, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConceptNameTagMap>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConceptNameTagMap[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
