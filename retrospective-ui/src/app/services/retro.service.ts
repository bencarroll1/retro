import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Retro} from '../models/retro';
import {Item} from '../models/item';
import {ActionItem} from '../models/actionItem';
import {SentimentAnalysis} from '../models/sentimentAnalysis';


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
  sentimentAnalysisSuffix = '/sentiment-analysis';
  exportItemsSuffix = '/export-items';
  exportActionItemsSuffix = '/export-actionItems';
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

  // method to update a retros contents by ID
  updateRetroById(updateRetro: Retro): void {
    this.http.put<Retro>(this.urlPrefix + updateRetro.id, updateRetro, this.httpOptions).subscribe(response => {
      this.retros = this.retros.filter((retro) => {
        return retro.id !== updateRetro.id;
      });
      this.retros.push(updateRetro);
      console.log(response);
    }, error => {
      console.error('Error updating item: ', error);
    });
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

  // method to delete a retrospective by ID
  deleteRetroById(retroId): Observable<void> {
    return this.http.delete<void>(this.urlPrefix + retroId, this.httpOptions);
  }

  // method to delete a retrospectives items by item id
  deleteRetroItemById(itemId): Observable<void> {
    return this.http.delete<void>(this.urlPrefixItems + itemId, this.httpOptions);
  }

  // method to delete a retrospectives action items by action item id
  deleteRetroActionItemById(actionItemId): Observable<void> {
    return this.http.delete<void>(this.urlPrefixActionItems + actionItemId, this.httpOptions);
  }

  // method to update a retrospective item by id
  updateRetroItemById(updateItem: Item): void {
    this.http.put<Item>(this.urlPrefixItems + updateItem.id, updateItem, this.httpOptions).subscribe(response => {
      this.items = this.items.filter((item) => {
        return item.id !== updateItem.id;
      });
      this.items.push(updateItem);
      console.log(response);
    }, error => {
      console.error('Error updating item: ', error);
    });
  }

  // method to update a retrospective action item by id
  updateRetroActionItemById(updateActionItem: ActionItem): void {
    this.http.put<ActionItem>(this.urlPrefixActionItems + updateActionItem.id, updateActionItem, this.httpOptions).subscribe(response => {
      this.actionItems = this.actionItems.filter((actionItem) => {
        return actionItem.id !== updateActionItem.id;
      });
      this.actionItems.push(updateActionItem);
      console.log(response);
    }, error => {
      console.error('Error updating item: ', error);
    });
  }

  // method to get a retrospectives items by ID from the API
  getRetroItemsByIdForSentimentAnalysis(id): Observable<HttpResponse<SentimentAnalysis>> {
    return this.http.get<SentimentAnalysis>(
      this.urlPrefix + id + this.itemsUrlSuffix + this.sentimentAnalysisSuffix,
      {observe: 'response'});
  }

  // method to get a retrospectives items by ID from the API and export to CSV
  getRetroItemsByIdAndExportToCSV(retroId): Observable<Blob> {
    const uri = this.urlPrefix + retroId + this.exportItemsSuffix;
    return this.http.get(uri, {responseType: 'blob'});
  }

  // method to get a retrospectives action items by ID from the API and export to CSV
  getRetroActionItemsByIdAndExportToCSV(retroId): Observable<Blob> {
    const uri = this.urlPrefix + retroId + this.exportActionItemsSuffix;
    return this.http.get(uri, {responseType: 'blob'});
  }

}
