import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddingNewComponent } from './adding-new.component';

describe('AddingNewComponent', () => {
  let component: AddingNewComponent;
  let fixture: ComponentFixture<AddingNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddingNewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddingNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
