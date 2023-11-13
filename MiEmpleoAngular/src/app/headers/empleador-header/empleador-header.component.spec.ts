import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpleadorHeaderComponent } from './empleador-header.component';

describe('EmpleadorHeaderComponent', () => {
  let component: EmpleadorHeaderComponent;
  let fixture: ComponentFixture<EmpleadorHeaderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmpleadorHeaderComponent]
    });
    fixture = TestBed.createComponent(EmpleadorHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
