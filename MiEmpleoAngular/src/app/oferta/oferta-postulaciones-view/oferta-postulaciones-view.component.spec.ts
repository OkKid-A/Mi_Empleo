import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfertaPostulacionesViewComponent } from './oferta-postulaciones-view.component';

describe('OfertaPostulacionesViewComponent', () => {
  let component: OfertaPostulacionesViewComponent;
  let fixture: ComponentFixture<OfertaPostulacionesViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OfertaPostulacionesViewComponent]
    });
    fixture = TestBed.createComponent(OfertaPostulacionesViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
