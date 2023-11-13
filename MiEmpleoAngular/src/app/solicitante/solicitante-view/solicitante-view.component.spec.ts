import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitanteViewComponent } from './solicitante-view.component';

describe('SolicitanteViewComponent', () => {
  let component: SolicitanteViewComponent;
  let fixture: ComponentFixture<SolicitanteViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SolicitanteViewComponent]
    });
    fixture = TestBed.createComponent(SolicitanteViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
