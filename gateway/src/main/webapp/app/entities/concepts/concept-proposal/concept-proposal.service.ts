import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConceptProposal } from 'app/shared/model/concepts/concept-proposal.model';

type EntityResponseType = HttpResponse<IConceptProposal>;
type EntityArrayResponseType = HttpResponse<IConceptProposal[]>;

@Injectable({ providedIn: 'root' })
export class ConceptProposalService {
  public resourceUrl = SERVER_API_URL + 'services/concepts/api/concept-proposals';

  constructor(protected http: HttpClient) {}

  create(conceptProposal: IConceptProposal): Observable<EntityResponseType> {
    return this.http.post<IConceptProposal>(this.resourceUrl, conceptProposal, { observe: 'response' });
  }

  update(conceptProposal: IConceptProposal): Observable<EntityResponseType> {
    return this.http.put<IConceptProposal>(this.resourceUrl, conceptProposal, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConceptProposal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConceptProposal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
