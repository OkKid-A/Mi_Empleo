import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCategoriasTableComponent } from './admin-categorias-table.component';

describe('AdminCategoriasListComponent', () => {
  let component: AdminCategoriasTableComponent;
  let fixture: ComponentFixture<AdminCategoriasTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminCategoriasTableComponent]
    });
    fixture = TestBed.createComponent(AdminCategoriasTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
