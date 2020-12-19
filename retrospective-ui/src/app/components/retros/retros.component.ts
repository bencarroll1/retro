import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {RetroService} from '../../services/retro.service';
import {Item} from '../../models/item';
import {ActionItem} from '../../models/actionItem';

import {Observable, Subscription} from 'rxjs';
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

  actionItemObservable: Observable<void>;
  actionItemSubscription: Subscription;
  actionItemInterval;

  @ViewChild('htmlDataGoodItems') htmlDataGoodItems: ElementRef;
  @ViewChild('htmlDataQuestionItems') htmlDataQuestionItems: ElementRef;
  @ViewChild('htmlDataBadItems') htmlDataBadItems: ElementRef;
  @ViewChild('htmlDataActionItems') htmlDataActionItems: ElementRef;

  constructor(private route: ActivatedRoute, private http: HttpClient, public retroService: RetroService, private modalService: NgbModal) {
  }

  ngOnInit() {
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
  }

  ngOnDestroy() {
    this.itemSubscription.unsubscribe();
    this.actionItemSubscription.unsubscribe();
    clearInterval(this.itemInterval);
    clearInterval(this.actionItemInterval);
  }

  getRetroItems() {
    console.log('fetching retro items');
    this.retroService.getRetroItemsById(this.id).subscribe(response => {
      this.retroService.items = this.sortItems(response.body);
    });
  }

  sortItems(itemsArray: Item[]): Item[] {
    return itemsArray.sort((a, b) => {
      return (a.itemFlag === b.itemFlag) ? 0 : a.itemFlag ? 1 : -1;
    });
  }

  getRetroActionItems() {
    console.log('fetching retro action items');
    this.retroService.getRetroActionItemsById(this.id).subscribe(response => {
      this.actionItems = response.body;
    });
  }

}
