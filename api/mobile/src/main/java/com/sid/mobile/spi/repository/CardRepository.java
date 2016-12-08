package com.sid.mobile.spi.repository;

import com.sid.mobile.model.Card;

public interface CardRepository {
    Card findByNumber(String number);
}
