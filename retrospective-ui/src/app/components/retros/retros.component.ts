import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {RetroService} from '../../services/retro.service';
import {Item} from '../../models/item';
import {ActionItem} from '../../models/actionItem';

import {Observable, Subscription} from 'rxjs';
// icons import
import {
  faArrowDown,
  faArrowLeft,
  faArrowUp,
  faCheck,
  faEdit,
  faExclamationCircle,
  faFrown,
  faQuestion,
  faSmile,
  faTrash
} from '@fortawesome/free-solid-svg-icons';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-retros',
  templateUrl: './retros.component.html',
  styleUrls: ['./retros.component.css']
})

export class RetrosComponent implements OnInit, OnDestroy {

  // icons
  faCheck = faCheck;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  faArrowLeft = faArrowLeft;
  faSmile = faSmile;
  faFrown = faFrown;
  faQuestion = faQuestion;
  faExclamationCircle = faExclamationCircle;
  faTrash = faTrash;
  faEdit = faEdit;
  actionItems: ActionItem[] = [];

  id = this.route.snapshot.paramMap.get('id');
  retroName: string;
  currentDate = new Date().toLocaleDateString();
  items: Item[];
  item: Item;
  editItem: Item;
  editActionItem: ActionItem;

  // observables
  itemObservable: Observable<void>;
  itemSubscription: Subscription;
  itemInterval;

  dateTime = new Date();

  actionItemObservable: Observable<void>;
  actionItemSubscription: Subscription;
  actionItemInterval;

  // adding item types (good, bad, question) and action items as child elements of a retro
  @ViewChild('htmlDataGoodItems') htmlDataGoodItems: ElementRef;
  @ViewChild('htmlDataQuestionItems') htmlDataQuestionItems: ElementRef;
  @ViewChild('htmlDataBadItems') htmlDataBadItems: ElementRef;
  @ViewChild('htmlDataActionItems') htmlDataActionItems: ElementRef;

  constructor(private route: ActivatedRoute, private http: HttpClient, public retroService: RetroService, private modalService: NgbModal) {
  }

  ngOnInit() {
    // get retro and contents by id start of this component and sub to it
    this.retroService.getRetroById(this.id).subscribe(response => this.retroName = response.body.name);

    // initial first load
    this.getRetroItems();
    this.getRetroActionItems();

    // create observables with 5 second intervals + subscribe to them
    this.itemObservable = new Observable<void>(observer => {
      this.items = this.retroService.items;
      this.itemInterval = setInterval(() => this.getRetroItems(), 5000);
    });
    this.itemSubscription = this.itemObservable.subscribe();

    this.actionItemObservable = new Observable<void>(observer => {
      this.actionItemInterval = setInterval(() => this.getRetroActionItems(), 5000);
    });
    this.actionItemSubscription = this.actionItemObservable.subscribe();

    this.retroService.getRetroItemsByIdAndExportToCSV(this.id);
  }

  ngOnDestroy() {
    this.itemSubscription.unsubscribe();
    this.actionItemSubscription.unsubscribe();
    clearInterval(this.itemInterval);
    clearInterval(this.actionItemInterval);
  }

  getRetroItems() {
    // get retros items
    console.log('fetching retro items');
    this.retroService.getRetroItemsById(this.id).subscribe(response => {
      this.retroService.items = this.sortItems(response.body);
    });
  }

  sortItems(itemsArray: Item[]): Item[] {
    // sort items by if item is done or not: method needs work
    return itemsArray.sort((a, b) => {
      return (a.itemFlag === b.itemFlag) ? 0 : a.itemFlag ? 1 : -1;
    });
  }

  getRetroActionItems() {
    // get retros action items
    console.log('fetching retro action items');
    this.retroService.getRetroActionItemsById(this.id).subscribe(response => {
      this.actionItems = response.body;
    });
  }

  updateItemDescription(item: Item) {
    console.log(item.id);
    console.log(item.description);
    this.retroService.updateRetroItemById(item);
  }

  deleteRetroItemById(itemId) {
    this.retroService.deleteRetroItemById(itemId).subscribe((response) => {
      this.retroService.items = this.retroService.items.filter((item) => {
        return item.id !== itemId;
      });
    });
  }

  deleteRetroActionItemById(actionItemId) {
    this.retroService.deleteRetroActionItemById(actionItemId).subscribe((response) => {
      this.actionItems = this.actionItems.filter((actionItem) => {
        return actionItem.id !== actionItemId;
      });
    });
  }

  openItem(content, itemId) {
    console.log(itemId);
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      console.log(`Modal result: ${result}`);
      if (result) {
        this.deleteRetroItemById(itemId);
      }
    }, (reason) => {
      console.log(`Modal dismissed: ${reason}`);
    });
  }

  openActionItem(content, actionItemId) {
    console.log(actionItemId);
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      console.log(`Modal result: ${result}`);
      if (result) {
        this.deleteRetroActionItemById(actionItemId);
      }
    }, (reason) => {
      console.log(`Modal dismissed: ${reason}`);
    });
  }

  itemIsDone(item: Item) {
    item.itemFlag = !item.itemFlag;
    console.log(item.id);
    console.log(item.itemFlag);
    this.retroService.updateRetroItemById(item);
  }

  upVoteItem(item: Item) {
    item.itemVotes = item.itemVotes + 1;
    console.log('Item ID: ' + item.id);
    console.log('Current vote: ' + item.itemVotes);
    this.retroService.updateRetroItemById(item);
  }

  downVoteItem(item: Item) {
    item.itemVotes = item.itemVotes - 1;
    console.log('Item ID: ' + item.id);
    console.log('Current vote: ' + item.itemVotes);
    this.retroService.updateRetroItemById(item);
  }

  openItemEditor(content, item) {
    console.log(item);
    this.editItem = JSON.parse(JSON.stringify(item));
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title-edit-item'}).result.then((result) => {
      console.log(`Modal result: ${result}`);
      if (result) {
        this.updateItemDescription(this.editItem);
      }
    }, (reason) => {
      console.log(`Modal dismissed: ${reason}`);
    });
  }

  updateActionItemDescription(actionItem: ActionItem) {
    console.log(actionItem.id);
    console.log(actionItem.description);
    this.retroService.updateRetroActionItemById(actionItem);
  }

  openActionItemEditor(content, actionItem) {
    console.log(actionItem);
    this.editActionItem = JSON.parse(JSON.stringify(actionItem));
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title-edit-action-item'}).result.then((result) => {
      console.log(`Modal result: ${result}`);
      if (result) {
        this.updateActionItemDescription(this.editActionItem);
      }
    }, (reason) => {
      console.log(`Modal dismissed: ${reason}`);
    });
  }

  getRetroItemsAndExportToCSV() {
    // get retros items and export to csv
    console.log('exporting retro items');
    this.retroService.getRetroItemsByIdAndExportToCSV(this.id).subscribe(response => {
      // It is necessary to create a new blob object with mime-type explicitly set
      // otherwise only Chrome works like it should
      const newBlob = new Blob([response], {type: 'application/csv'});

      // IE doesn't allow using a blob object directly as link href
      // instead it is necessary to use msSaveOrOpenBlob
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(newBlob);
        return;
      }

      // For other browsers:
      // Create a link pointing to the ObjectURL containing the blob.
      const data = window.URL.createObjectURL(newBlob);

      const link = document.createElement('a');
      link.href = data;
      link.download = 'Retrospective Items ' + this.dateTime + '.csv';
      // this is necessary as link.click() does not work on the latest firefox
      link.dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));

      // tslint:disable-next-line:only-arrow-functions
      setTimeout(function() {
        // For Firefox it is necessary to delay revoking the ObjectURL
        window.URL.revokeObjectURL(data);
        link.remove();
      }, 100);
    });
  }

  getRetroActionItemsAndExportToCSV() {
    // get retros action items and export to csv
    console.log('exporting retro action items');
    this.retroService.getRetroActionItemsByIdAndExportToCSV(this.id).subscribe(response => {
      // It is necessary to create a new blob object with mime-type explicitly set
      // otherwise only Chrome works like it should
      const newBlob = new Blob([response], {type: 'application/csv'});

      // IE doesn't allow using a blob object directly as link href
      // instead it is necessary to use msSaveOrOpenBlob
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(newBlob);
        return;
      }

      // For other browsers:
      // Create a link pointing to the ObjectURL containing the blob.
      const data = window.URL.createObjectURL(newBlob);

      const link = document.createElement('a');
      link.href = data;
      link.download = 'Retrospective Action Items ' + this.dateTime + '.csv';
      // this is necessary as link.click() does not work on the latest firefox
      link.dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));

      // tslint:disable-next-line:only-arrow-functions
      setTimeout(function() {
        // For Firefox it is necessary to delay revoking the ObjectURL
        window.URL.revokeObjectURL(data);
        link.remove();
      }, 100);
    });
  }
}
