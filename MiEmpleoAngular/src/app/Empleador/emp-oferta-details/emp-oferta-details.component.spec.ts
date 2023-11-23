import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpOfertaDetailsComponent } from './emp-oferta-details.component';

describe('EmpOfertaDetailsComponent', () => {
  let component: EmpOfertaDetailsComponent;
  let fixture: ComponentFixture<EmpOfertaDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmpOfertaDetailsComponent]
    });
    fixture = TestBed.createComponent(EmpOfertaDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
