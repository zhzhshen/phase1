package com.sid.spi.repository;

import com.sid.model.Card;
import com.sid.model.Contract;

public interface ContractRepository {
    Contract findByCard(Card card);
}
