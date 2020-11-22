import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFormField } from 'app/shared/model/forms/form-field.model';

type EntityResponseType = HttpResponse<IFormField>;
type EntityArrayResponseType = HttpResponse<IFormField[]>;

@Injectable({ providedIn: 'root' })
export class FormFieldService {
  public resourceUrl = SERVER_API_URL + 'services/forms/api/form-fields';

  constructor(protected http: HttpClient) {}

  create(formField: IFormField): Observable<EntityResponseType> {
    return this.http.post<IFormField>(this.resourceUrl, formField, { observe: 'response' });
  }

  update(formField: IFormField): Observable<EntityResponseType> {
    return this.http.put<IFormField>(this.resourceUrl, formField, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFormField>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFormField[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
