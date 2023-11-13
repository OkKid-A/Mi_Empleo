import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NuevaOfertaFormComponent } from './nueva-oferta-form.component';

describe('NuevaOfertaFormComponent', () => {
  let component: NuevaOfertaFormComponent;
  let fixture: ComponentFixture<NuevaOfertaFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NuevaOfertaFormComponent]
    });
    fixture = TestBed.createComponent(NuevaOfertaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
