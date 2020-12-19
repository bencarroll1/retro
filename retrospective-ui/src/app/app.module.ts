import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {RetrosListComponent} from './components/retros-list/retros-list.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RetrosComponent} from './components/retros/retros.component';
import {ItemFormComponent} from './components/item-form/item-form.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ActionItemFormComponent} from './components/action-item-form/action-item-form.component';
@NgModule({
  declarations: [
    AppComponent,
    RetrosListComponent,
    RetrosComponent,
    ItemFormComponent,
    ActionItemFormComponent
  ],
  imports: [
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
