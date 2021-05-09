import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {RetrosListComponent} from './components/retros-list/retros-list.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RetrosComponent} from './components/retros/retros.component';
import {RetroItemPipe} from './components/retros/retro-item.pipe';
import {RetroPipe} from './components/retros-list/retro.pipe';
import {ItemFormComponent} from './components/item-form/item-form.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ActionItemFormComponent} from './components/action-item-form/action-item-form.component';
import {RetroItemVotesPipe} from './components/retros/retro-item-votes.pipe';
import {ArchivedRetrosComponent} from './components/archived-retros/archived-retros.component';
import {RetroArchivedPipe} from './components/retros-list/retro-archived.pipe';
import {SentimentAnalysisComponent} from './components/sentiment-analysis/sentiment-analysis.component';
import { SentimentAnalysisListComponent } from './components/sentiment-analysis-list/sentiment-analysis-list.component';

@NgModule({
  declarations: [
    // declaring components
    AppComponent,
    RetrosListComponent,
    RetrosComponent,
    RetroItemPipe,
    RetroItemVotesPipe,
    RetroPipe,
    ItemFormComponent,
    ActionItemFormComponent,
    ArchivedRetrosComponent,
    RetroArchivedPipe,
    SentimentAnalysisComponent,
    SentimentAnalysisListComponent
  ],
  imports: [
    // declaring imports
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    FontAwesomeModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
