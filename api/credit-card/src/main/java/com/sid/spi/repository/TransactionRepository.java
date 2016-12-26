package com.sid.spi.repository;

import com.sid.model.Card;
import com.sid.model.Transaction;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> findByCard(Card card);
}
