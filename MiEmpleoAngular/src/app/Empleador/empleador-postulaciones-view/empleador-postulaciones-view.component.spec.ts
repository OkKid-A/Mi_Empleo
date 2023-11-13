import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpleadorPostulacionesViewComponent } from './empleador-postulaciones-view.component';

describe('EmpleadorPostulacionesViewComponent', () => {
  let component: EmpleadorPostulacionesViewComponent;
  let fixture: ComponentFixture<EmpleadorPostulacionesViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmpleadorPostulacionesViewComponent]
    });
    fixture = TestBed.createComponent(EmpleadorPostulacionesViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
