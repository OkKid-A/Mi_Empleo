import { TestBed } from '@angular/core/testing';

import { ElectorService } from './elector.service';

describe('ElectorService', () => {
  let service: ElectorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ElectorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
