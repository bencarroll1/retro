import {Component, OnInit} from '@angular/core';

import {faArchive, faChartLine, faHome} from '@fortawesome/free-solid-svg-icons';
import {ActivatedRoute} from '@angular/router';
import {RetroService} from '../../services/retro.service';

@Component({
  selector: 'app-sentiment-analysis',
  templateUrl: './sentiment-analysis.component.html',
  styleUrls: ['./sentiment-analysis.component.css']
})
export class SentimentAnalysisComponent implements OnInit {

  // icons
  faArchive = faArchive;
  faHome = faHome;
  faChartLine = faChartLine;

  retroName: string;
  sentimentAnalysisScore: number;
  sent: string;
  id = this.route.snapshot.paramMap.get('id');

  constructor(private route: ActivatedRoute, public retroService: RetroService) {
  }

  ngOnInit(): void {
    this.retroService.getRetroById(this.id).subscribe(response => this.retroName = response.body.name);
    // get retros sentiment analysis on init
    this.retroService.getRetroItemsByIdForSentimentAnalysis(this.id)
      .subscribe(response => this.sentimentAnalysisScore = (response.body.score * 10)
      );
  }

  getRetroSentimentAnalysis() {
    // get retros sentiment analysis
    console.log('fetching retro sentiment analysis');
    this.retroService.getRetroItemsByIdForSentimentAnalysis(this.id).subscribe(response => {
      console.log('hello');
      console.log(response);
      return response.body;
    });
  }

}
