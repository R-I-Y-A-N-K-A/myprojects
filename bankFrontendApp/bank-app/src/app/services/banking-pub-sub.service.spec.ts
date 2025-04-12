import { TestBed } from '@angular/core/testing';

import { BankingPubSubService } from './banking-pub-sub.service';

describe('BankingPubSubService', () => {
  let service: BankingPubSubService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BankingPubSubService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
