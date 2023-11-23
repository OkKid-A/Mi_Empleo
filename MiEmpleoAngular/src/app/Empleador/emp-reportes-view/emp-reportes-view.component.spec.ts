import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpReportesViewComponent } from './emp-reportes-view.component';

describe('EmpReportesViewComponent', () => {
  let component: EmpReportesViewComponent;
  let fixture: ComponentFixture<EmpReportesViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmpReportesViewComponent]
    });
    fixture = TestBed.createComponent(EmpReportesViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
