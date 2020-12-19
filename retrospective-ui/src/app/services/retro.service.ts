import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Retro} from '../models/retro';
import {Item} from '../models/item';
import {ActionItem} from '../models/actionItem';


@Injectable({
  providedIn: 'root'
})

export class RetroService {
  urlPrefix = '/api/retros/';
  urlPrefixItems = '/api/items/';
  urlPrefixActionItems = '/api/action-items/';
  itemsUrlSuffix = '/items';
  actionItemsUrlSuffix = '/action-items';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  public items: Item[] = [];
  public retros: Retro[] = [];
  public actionItems: ActionItem[] = [];

  constructor(private http: HttpClient) {
  }

  getRetros(): Observable<HttpResponse<Retro[]>> {
    return this.http.get<Retro[]>(
      this.urlPrefix, {observe: 'response'});
  }

  addRetro(retro: Retro): Observable<Retro> {
    return this.http.post<Retro>(this.urlPrefix, retro, this.httpOptions);
  }

  getRetroById(id): Observable<HttpResponse<Retro>> {
    return this.http.get<Retro>(this.urlPrefix + id, {observe: 'response'});
  }

  getRetroItemsById(id): Observable<HttpResponse<Item[]>> {
    return this.http.get<Item[]>(this.urlPrefix + id + this.itemsUrlSuffix, {observe: 'response'});
  }

  getRetroActionItemsById(id): Observable<HttpResponse<ActionItem[]>> {
    return this.http.get<ActionItem[]>(this.urlPrefix + id + this.actionItemsUrlSuffix, {observe: 'response'});
  }

  addItemToRetro(item: Item, id): Observable<Item> {
    return this.http.post<Item>(this.urlPrefix + id + this.itemsUrlSuffix, item, this.httpOptions);
  }

  addActionItemToRetro(actionItem: ActionItem, id): Observable<ActionItem> {
    return this.http.post<ActionItem>(this.urlPrefix + id + this.actionItemsUrlSuffix, actionItem, this.httpOptions);
  }

}
