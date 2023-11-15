import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitanteFormComponent } from './solicitante-form.component';

describe('SolicitanteFormComponent', () => {
  let component: SolicitanteFormComponent;
  let fixture: ComponentFixture<SolicitanteFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SolicitanteFormComponent]
    });
    fixture = TestBed.createComponent(SolicitanteFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
