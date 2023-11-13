import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpleadorOfertasViewComponent } from './empleador-ofertas-view.component';

describe('EmpleadorOfertasViewComponent', () => {
  let component: EmpleadorOfertasViewComponent;
  let fixture: ComponentFixture<EmpleadorOfertasViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmpleadorOfertasViewComponent]
    });
    fixture = TestBed.createComponent(EmpleadorOfertasViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
