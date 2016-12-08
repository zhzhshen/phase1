package com.sid.mobile.spi.repository;

import com.sid.mobile.model.Card;
import com.sid.mobile.model.Topup;

import java.util.List;
import java.util.Map;

public interface TopupRepository {
    List<Topup> findByNumber(String number);

    long create(Card card, Map<String, Object> info);

    Topup findById(long id);
}
