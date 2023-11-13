import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntrevistaListComponent } from './entrevista-list.component';

describe('EntrevistaListComponent', () => {
  let component: EntrevistaListComponent;
  let fixture: ComponentFixture<EntrevistaListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EntrevistaListComponent]
    });
    fixture = TestBed.createComponent(EntrevistaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
