import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CargadorArchivoComponent } from './cargador-archivo.component';

describe('CargadorArchivoComponent', () => {
  let component: CargadorArchivoComponent;
  let fixture: ComponentFixture<CargadorArchivoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CargadorArchivoComponent]
    });
    fixture = TestBed.createComponent(CargadorArchivoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
