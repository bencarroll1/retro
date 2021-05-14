import {Component, Input, OnInit} from '@angular/core';
import {Item} from 'src/app/models/item';
import {RetroService} from 'src/app/services/retro.service';
import {faCheck} from '@fortawesome/free-solid-svg-icons';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-item-form',
  templateUrl: './item-form.component.html',
  styleUrls: ['./item-form.component.css']
})
export class ItemFormComponent implements OnInit {

  // icons
  faCheck = faCheck;
  @Input() retroId: number;
  @Input() itemType: string;
  description: string;

  itemForm: FormGroup;

  constructor(private retroService: RetroService) {
  }

  ngOnInit(): void {
    this.itemForm = new FormGroup({
      // preforming validation on entries to the item form
      description: new FormControl(this.description, [
        Validators.required
      ])
    });
  }

  onSubmit() {
    const item: Item = {
      description: this.description,
      type: this.itemType
    };
    // on submission call the add item to retro method, add the item to the specified retro
    this.retroService.addItemToRetro(item, this.retroId).subscribe((item: Item) => {
      this.retroService.items.unshift(item);
      console.log(item);
    });
  }
}
