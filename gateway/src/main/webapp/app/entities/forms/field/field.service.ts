import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IField } from 'app/shared/model/forms/field.model';

type EntityResponseType = HttpResponse<IField>;
type EntityArrayResponseType = HttpResponse<IField[]>;

@Injectable({ providedIn: 'root' })
export class FieldService {
  public resourceUrl = SERVER_API_URL + 'services/forms/api/fields';

  constructor(protected http: HttpClient) {}

  create(field: IField): Observable<EntityResponseType> {
    return this.http.post<IField>(this.resourceUrl, field, { observe: 'response' });
  }

  update(field: IField): Observable<EntityResponseType> {
    return this.http.put<IField>(this.resourceUrl, field, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IField>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IField[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
