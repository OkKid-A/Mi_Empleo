import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpEntrevistasViewComponent } from './emp-entrevistas-view.component';

describe('EmpEntrevistasViewComponent', () => {
  let component: EmpEntrevistasViewComponent;
  let fixture: ComponentFixture<EmpEntrevistasViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmpEntrevistasViewComponent]
    });
    fixture = TestBed.createComponent(EmpEntrevistasViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
