import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminReportesViewComponent } from './admin-reportes-view.component';

describe('AdminReportesViewComponent', () => {
  let component: AdminReportesViewComponent;
  let fixture: ComponentFixture<AdminReportesViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminReportesViewComponent]
    });
    fixture = TestBed.createComponent(AdminReportesViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
