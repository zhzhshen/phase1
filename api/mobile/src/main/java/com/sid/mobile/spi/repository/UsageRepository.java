package com.sid.mobile.spi.repository;

import com.sid.mobile.model.Card;
import com.sid.mobile.spi.model.Usage;

import java.util.List;
import java.util.Map;

public interface UsageRepository {
    String create(Card card, Map<String, Object> info);

    Usage findById(String id);

    List<Usage> findByNumber(String number);
}
