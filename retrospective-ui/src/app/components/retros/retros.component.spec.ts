import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RetrosComponent} from './retros.component';

describe('RetrosComponent', () => {
  let component: RetrosComponent;
  let fixture: ComponentFixture<RetrosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RetrosComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RetrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
