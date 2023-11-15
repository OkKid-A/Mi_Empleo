import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriasCheckboxComponent } from './categorias-checkbox.component';

describe('CategoriasCheckboxComponent', () => {
  let component: CategoriasCheckboxComponent;
  let fixture: ComponentFixture<CategoriasCheckboxComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategoriasCheckboxComponent]
    });
    fixture = TestBed.createComponent(CategoriasCheckboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
