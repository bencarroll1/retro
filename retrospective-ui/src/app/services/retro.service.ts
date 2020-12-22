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
  // prefixes and suffix for endpoints
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
  // declaring instances of each models as arrays
  public items: Item[] = [];
  public retros: Retro[] = [];
  public actionItems: ActionItem[] = [];

  constructor(private http: HttpClient) {
  }

  // method to get all retrospectives from API
  getRetros(): Observable<HttpResponse<Retro[]>> {
    return this.http.get<Retro[]>(
      this.urlPrefix, {observe: 'response'});
  }

  // method to add a retrospective to the database via the API
  addRetro(retro: Retro): Observable<Retro> {
    return this.http.post<Retro>(this.urlPrefix, retro, this.httpOptions);
  }

  // method to get a retrospective from the API by ID
  getRetroById(id): Observable<HttpResponse<Retro>> {
    return this.http.get<Retro>(this.urlPrefix + id, {observe: 'response'});
  }

  // method to get a retrospectives items by ID from the API
  getRetroItemsById(id): Observable<HttpResponse<Item[]>> {
    return this.http.get<Item[]>(this.urlPrefix + id + this.itemsUrlSuffix, {observe: 'response'});
  }

  // method to get a retrospectives action items by ID from the API
  getRetroActionItemsById(id): Observable<HttpResponse<ActionItem[]>> {
    return this.http.get<ActionItem[]>(this.urlPrefix + id + this.actionItemsUrlSuffix, {observe: 'response'});
  }

  // method to add an item to a retrospective via the API
  addItemToRetro(item: Item, id): Observable<Item> {
    return this.http.post<Item>(this.urlPrefix + id + this.itemsUrlSuffix, item, this.httpOptions);
  }

  // method to add an action item to a retrospective via the API
  addActionItemToRetro(actionItem: ActionItem, id): Observable<ActionItem> {
    return this.http.post<ActionItem>(this.urlPrefix + id + this.actionItemsUrlSuffix, actionItem, this.httpOptions);
  }

}
