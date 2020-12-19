import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RetrosListComponent} from './retros-list.component';

describe('RetrosListComponent', () => {
  let component: RetrosListComponent;
  let fixture: ComponentFixture<RetrosListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RetrosListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RetrosListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
