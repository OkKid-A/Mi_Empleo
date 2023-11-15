import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpleadorFormComponent } from './empleador-form.component';

describe('EmpleadorFormComponent', () => {
  let component: EmpleadorFormComponent;
  let fixture: ComponentFixture<EmpleadorFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmpleadorFormComponent]
    });
    fixture = TestBed.createComponent(EmpleadorFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
