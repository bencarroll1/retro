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

  // MODAL STUFF
  open(content, retroId) {
    console.log(retroId);
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      console.log(`Modal result: ${result}`);
      // a true result from modal means delete the retro
      if (result) {
        this.deleteRetroById(retroId);
      }
    }, (reason) => {
      console.log(`Modal dismissed: ${reason}`);
    });
  }

  archiveRetro(retro: Retro) {
    retro.archiveRetroFlag = !retro.archiveRetroFlag;
    console.log('Retro (ID: ' + retro.id + ') archive status: ' + retro.archiveRetroFlag);
    this.retroService.updateRetroById(retro);
  }

  private deleteRetroById(retroId) {
    this.retroService.deleteRetroById(retroId).subscribe((response) => {
      // successfully deleted now filter it out of the retros array to the UI is updated
      this.retros = this.retros.filter((retro) => {
        return retro.id !== retroId;
      });
    });
  }
}
