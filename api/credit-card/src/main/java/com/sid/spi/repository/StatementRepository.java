package com.sid.spi.repository;

import com.sid.model.Card;
import com.sid.model.Statement;

import java.util.List;
import java.util.Map;

public interface StatementRepository {
    List<Statement> findByCard(Card card);

    Statement save(Map<String, Object> info);

    Statement findById(String id);
}
