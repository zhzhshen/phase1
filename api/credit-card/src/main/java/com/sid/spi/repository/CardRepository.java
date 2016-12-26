package com.sid.spi.repository;

import com.sid.model.Card;
import com.sid.spi.model.User;

import java.util.List;
import java.util.Map;

public interface CardRepository {
    Card create(Map<String, Object> info);

    List<Card> findByUser(User user);

    Card findById(String id);
}
