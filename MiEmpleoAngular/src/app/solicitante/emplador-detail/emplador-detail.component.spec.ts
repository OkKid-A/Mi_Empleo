import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpladorDetailComponent } from './emplador-detail.component';

describe('EmpladorDetailComponent', () => {
  let component: EmpladorDetailComponent;
  let fixture: ComponentFixture<EmpladorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmpladorDetailComponent]
    });
    fixture = TestBed.createComponent(EmpladorDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
