import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SentimentAnalysisListComponent } from './sentiment-analysis-list.component';

describe('SentimentAnalysisListComponent', () => {
  let component: SentimentAnalysisListComponent;
  let fixture: ComponentFixture<SentimentAnalysisListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SentimentAnalysisListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SentimentAnalysisListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
