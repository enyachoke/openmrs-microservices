import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConceptProposalTagMap } from 'app/shared/model/concepts/concept-proposal-tag-map.model';

type EntityResponseType = HttpResponse<IConceptProposalTagMap>;
type EntityArrayResponseType = HttpResponse<IConceptProposalTagMap[]>;

@Injectable({ providedIn: 'root' })
export class ConceptProposalTagMapService {
  public resourceUrl = SERVER_API_URL + 'services/concepts/api/concept-proposal-tag-maps';

  constructor(protected http: HttpClient) {}

  create(conceptProposalTagMap: IConceptProposalTagMap): Observable<EntityResponseType> {
    return this.http.post<IConceptProposalTagMap>(this.resourceUrl, conceptProposalTagMap, { observe: 'response' });
  }

  update(conceptProposalTagMap: IConceptProposalTagMap): Observable<EntityResponseType> {
    return this.http.put<IConceptProposalTagMap>(this.resourceUrl, conceptProposalTagMap, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConceptProposalTagMap>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConceptProposalTagMap[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
