import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitanteBuscadorComponent } from './solicitante-buscador.component';

describe('SolicitanteBuscadorComponent', () => {
  let component: SolicitanteBuscadorComponent;
  let fixture: ComponentFixture<SolicitanteBuscadorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SolicitanteBuscadorComponent]
    });
    fixture = TestBed.createComponent(SolicitanteBuscadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
