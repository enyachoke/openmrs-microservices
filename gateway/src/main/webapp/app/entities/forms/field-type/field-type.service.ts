import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFieldType } from 'app/shared/model/forms/field-type.model';

type EntityResponseType = HttpResponse<IFieldType>;
type EntityArrayResponseType = HttpResponse<IFieldType[]>;

@Injectable({ providedIn: 'root' })
export class FieldTypeService {
  public resourceUrl = SERVER_API_URL + 'services/forms/api/field-types';

  constructor(protected http: HttpClient) {}

  create(fieldType: IFieldType): Observable<EntityResponseType> {
    return this.http.post<IFieldType>(this.resourceUrl, fieldType, { observe: 'response' });
  }

  update(fieldType: IFieldType): Observable<EntityResponseType> {
    return this.http.put<IFieldType>(this.resourceUrl, fieldType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFieldType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFieldType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
