import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitanteHeaderComponent } from './solicitante-header.component';

describe('SolicitanteHeaderComponent', () => {
  let component: SolicitanteHeaderComponent;
  let fixture: ComponentFixture<SolicitanteHeaderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SolicitanteHeaderComponent]
    });
    fixture = TestBed.createComponent(SolicitanteHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
