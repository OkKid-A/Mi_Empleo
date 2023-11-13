import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostulacionesListComponent } from './postulaciones-list.component';

describe('PostulacionesListComponent', () => {
  let component: PostulacionesListComponent;
  let fixture: ComponentFixture<PostulacionesListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PostulacionesListComponent]
    });
    fixture = TestBed.createComponent(PostulacionesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
