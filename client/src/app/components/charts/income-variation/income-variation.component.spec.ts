import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IncomeVariationComponent } from './income-variation.component';

describe('IncomeVariationComponent', () => {
  let component: IncomeVariationComponent;
  let fixture: ComponentFixture<IncomeVariationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IncomeVariationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IncomeVariationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
