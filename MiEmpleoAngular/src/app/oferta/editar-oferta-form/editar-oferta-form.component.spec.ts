import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarOfertaFormComponent } from './editar-oferta-form.component';

describe('EditarOfertaFormComponent', () => {
  let component: EditarOfertaFormComponent;
  let fixture: ComponentFixture<EditarOfertaFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditarOfertaFormComponent]
    });
    fixture = TestBed.createComponent(EditarOfertaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
