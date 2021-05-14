import {Component, OnInit} from '@angular/core';

import {faArchive, faChartLine, faHome, faSearch, faSignInAlt} from '@fortawesome/free-solid-svg-icons';
import {Retro} from '../../models/retro';
import {RetroService} from '../../services/retro.service';

@Component({
  selector: 'app-sentiment-analysis-list',
  templateUrl: './sentiment-analysis-list.component.html',
  styleUrls: ['./sentiment-analysis-list.component.css']
})
export class SentimentAnalysisListComponent implements OnInit {

  // icons
  faArchive = faArchive;
  faHome = faHome;
  faChartLine = faChartLine;
  faSearch = faSearch;
  faSignInAlt = faSignInAlt;

  // creating array of retrospectives to be displayed on this page
  retros: Retro[] = [];

  sentimentAnalysisListRetroFilter: string;

  constructor(private retroService: RetroService) {
  }

  // retrospectives will be retrieved upon initialisation of this page
  ngOnInit(): void {
    this.retroService.getRetros().subscribe((response) => {
      this.retros = this.sortRetros(response.body);
    });
  }

  // method to sort retrospectives according to date of creation
  sortRetros(retrosArray: Retro[]): Retro[] {
    // sort retrospectives by date
    return retrosArray.sort((a, b) => {
      return (new Date(b.created) as any) - (new Date(a.created) as any);
    });
  }

}
