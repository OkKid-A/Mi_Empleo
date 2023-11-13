import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostulacionesViewComponent } from './postulaciones-view.component';

describe('PostulacionesViewComponent', () => {
  let component: PostulacionesViewComponent;
  let fixture: ComponentFixture<PostulacionesViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PostulacionesViewComponent]
    });
    fixture = TestBed.createComponent(PostulacionesViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
