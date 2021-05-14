import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RetrosListComponent} from './components/retros-list/retros-list.component';
import {RetrosComponent} from './components/retros/retros.component';
import {ArchivedRetrosComponent} from './components/archived-retros/archived-retros.component';
import {SentimentAnalysisComponent} from './components/sentiment-analysis/sentiment-analysis.component';
import {SentimentAnalysisListComponent} from './components/sentiment-analysis-list/sentiment-analysis-list.component';

// routing for frontend endpoints - component specified is served at specified endpoint
const routes: Routes =
  [{path: 'retros', component: RetrosListComponent},
    {
      path: '',
      redirectTo: '/retros',
      pathMatch: 'full'
    },
    {path: 'retros/:id', component: RetrosComponent},
    {path: 'archived-retros', component: ArchivedRetrosComponent},
    {path: 'retros/:id/sentiment-analysis', component: SentimentAnalysisComponent},
    {path: 'sentiment-analysis', component: SentimentAnalysisListComponent}
  ];

// hash used to hide api endpoint
const routerConfig = {
  useHash: true
};

@NgModule({
  imports: [RouterModule.forRoot(routes, routerConfig)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
