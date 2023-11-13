import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpleadorViewComponent } from './empleador-view.component';

describe('EmpleadorViewComponent', () => {
  let component: EmpleadorViewComponent;
  let fixture: ComponentFixture<EmpleadorViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmpleadorViewComponent]
    });
    fixture = TestBed.createComponent(EmpleadorViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
