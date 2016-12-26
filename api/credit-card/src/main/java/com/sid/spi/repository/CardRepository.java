package com.sid.spi.repository;

import com.sid.model.Card;

import java.util.Map;

public interface CardRepository {
    Card create(Map<String, Object> info);
}
