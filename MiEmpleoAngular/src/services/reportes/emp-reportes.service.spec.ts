import { TestBed } from '@angular/core/testing';

import { EmpReportesService } from './emp-reportes.service';

describe('EmpReportesService', () => {
  let service: EmpReportesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmpReportesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
