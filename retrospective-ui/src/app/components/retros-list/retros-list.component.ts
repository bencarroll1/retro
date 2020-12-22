import {Component, OnInit} from '@angular/core';
import {RetroService} from '../../services/retro.service';
import {Retro} from '../../models/retro';

import {faArchive, faCheck, faExclamationCircle, faHome, faSearch, faSignInAlt, faTrash} from '@fortawesome/free-solid-svg-icons';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-retros-list',
  templateUrl: './retros-list.component.html',
  styleUrls: ['./retros-list.component.css']
})
export class RetrosListComponent implements OnInit {

  // icons
  faCheck = faCheck;
  faTrash = faTrash;
  faSignInAlt = faSignInAlt;
  faSearch = faSearch;
  faExclamationCircle = faExclamationCircle;
  faArchive = faArchive;
  faHome = faHome;

  retroNameFilter: string;
  retroName: string;
  retroCurrent: true;

  retros: Retro[] = [];
  retroForm: FormGroup;

  // form for creating a new retrospective
  constructor(private retroService: RetroService, private modalService: NgbModal) {
    this.retroForm = new FormGroup({
      retroName: new FormControl(this.retroName, [
        Validators.required
      ])
    });
  }

  ngOnInit(): void {
    // on start of component, get all retrospectives
    this.retroService.getRetros().subscribe((response) => {
      this.retros = this.sortRetros(response.body);
    });
  }

  onSubmit() {
    const retro = {
      name: this.retroName
    };
    this.retroService.addRetro(retro).subscribe((createdRetro: Retro) => {
      // added create retro to the start of retros array
      this.retros.unshift(createdRetro);
    });
  }

  sortRetros(retrosArray: Retro[]): Retro[] {
    // sort retrospectives by date
    return retrosArray.sort((a, b) => {
      return (new Date(b.created) as any) - (new Date(a.created) as any);
    });
  }

}
