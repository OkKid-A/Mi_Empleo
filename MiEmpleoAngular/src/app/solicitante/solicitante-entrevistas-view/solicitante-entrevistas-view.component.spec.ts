import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitanteEntrevistasViewComponent } from './solicitante-entrevistas-view.component';

describe('SolicitanteEntrevistasViewComponent', () => {
  let component: SolicitanteEntrevistasViewComponent;
  let fixture: ComponentFixture<SolicitanteEntrevistasViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SolicitanteEntrevistasViewComponent]
    });
    fixture = TestBed.createComponent(SolicitanteEntrevistasViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
