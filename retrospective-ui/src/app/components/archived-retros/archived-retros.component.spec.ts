import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArchivedRetrosComponent } from './archived-retros.component';

describe('ArchivedRetrosComponent', () => {
  let component: ArchivedRetrosComponent;
  let fixture: ComponentFixture<ArchivedRetrosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArchivedRetrosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArchivedRetrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
