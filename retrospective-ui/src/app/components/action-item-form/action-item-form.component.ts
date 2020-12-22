import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {RetroService} from '../../services/retro.service';
import {faCheck} from '@fortawesome/free-solid-svg-icons';
import {ActionItem} from '../../models/actionItem';

@Component({
  selector: 'app-action-item-form',
  templateUrl: './action-item-form.component.html',
  styleUrls: ['./action-item-form.component.css']
})
export class ActionItemFormComponent implements OnInit {

  // icons
  faCheck = faCheck;
  @Input() retroId: number;
  description: string;

  actionItemForm: FormGroup;

  constructor(private retroService: RetroService) {
  }

  ngOnInit(): void {
    this.actionItemForm = new FormGroup({
      // preforming validation on entries to the action item form
      description: new FormControl(this.description, [
        Validators.required,
        Validators.maxLength(100)
      ])
    });
  }

  onSubmit() {
    const actionItem: ActionItem = {
      description: this.description,
    };
    // on submission call the add action item to retro method, add the action item to the specified retro
    this.retroService.addActionItemToRetro(actionItem, this.retroId).subscribe((actionItem: ActionItem) => {
      console.log(actionItem);
    });
  }
}
