import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntrevistasTableComponent } from './entrevistas-table.component';

describe('EntrevistasTableComponent', () => {
  let component: EntrevistasTableComponent;
  let fixture: ComponentFixture<EntrevistasTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EntrevistasTableComponent]
    });
    fixture = TestBed.createComponent(EntrevistasTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
