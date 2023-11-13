import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidateFormComponent } from './validate-form.component';

describe('ValidateFormComponent', () => {
  let component: ValidateFormComponent;
  let fixture: ComponentFixture<ValidateFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ValidateFormComponent]
    });
    fixture = TestBed.createComponent(ValidateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
