import {Component, OnInit} from '@angular/core';

import {faArchive, faHome, faSearch, faSignInAlt, faTimes, faChartLine} from '@fortawesome/free-solid-svg-icons';
import {Retro} from '../../models/retro';
import {RetroService} from '../../services/retro.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-archived-retros',
  templateUrl: './archived-retros.component.html',
  styleUrls: ['./archived-retros.component.css']
})
export class ArchivedRetrosComponent implements OnInit {

  retroNameFilter: string;

  // icons
  faSignInAlt = faSignInAlt;
  faSearch = faSearch;
  faArchive = faArchive;
  faHome = faHome;
  faTimes = faTimes;
  faChartLine = faChartLine;

  retros: Retro[] = [];


  constructor(private retroService: RetroService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.retroService.getRetros().subscribe((response) => {
      this.retros = this.sortRetros(response.body);
    });
  }

  sortRetros(retrosArray: Retro[]): Retro[] {
    return retrosArray.sort((a, b) => {
      return (new Date(b.created) as any) - (new Date(a.created) as any);
    });
  }

  archiveRetro(retro: Retro) {
    retro.archiveRetroFlag = !retro.archiveRetroFlag;
    console.log('Retro (ID: ' + retro.id + ') archive status: ' + retro.archiveRetroFlag);
    this.retroService.updateRetroById(retro);
  }

}
