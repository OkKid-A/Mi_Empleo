import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntrevistaCardComponent } from './entrevista-card.component';

describe('EntrevistaCardComponent', () => {
  let component: EntrevistaCardComponent;
  let fixture: ComponentFixture<EntrevistaCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EntrevistaCardComponent]
    });
    fixture = TestBed.createComponent(EntrevistaCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
