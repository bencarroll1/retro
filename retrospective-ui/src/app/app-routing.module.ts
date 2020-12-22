import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RetrosListComponent} from './components/retros-list/retros-list.component';
import {RetrosComponent} from './components/retros/retros.component';

// routing for frontend endpoints
const routes: Routes =
  [{path: 'retros', component: RetrosListComponent},
    {
      path: '',
      redirectTo: '/retros',
      pathMatch: 'full'
    },
    {path: 'retros/:id', component: RetrosComponent}];

const routerConfig = {
  useHash: true
};

@NgModule({
  imports: [RouterModule.forRoot(routes, routerConfig)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
