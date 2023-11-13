import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearCuentaFormComponent } from './crear-cuenta-form.component';

describe('CrearCuentaFormComponent', () => {
  let component: CrearCuentaFormComponent;
  let fixture: ComponentFixture<CrearCuentaFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CrearCuentaFormComponent]
    });
    fixture = TestBed.createComponent(CrearCuentaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
