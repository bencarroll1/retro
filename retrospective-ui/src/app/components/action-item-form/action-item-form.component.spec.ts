import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ActionItemFormComponent} from './action-item-form.component';

describe('ActionItemFormComponent', () => {
  let component: ActionItemFormComponent;
  let fixture: ComponentFixture<ActionItemFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ActionItemFormComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActionItemFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
