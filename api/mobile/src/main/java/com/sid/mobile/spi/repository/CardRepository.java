package com.sid.mobile.spi.repository;

import com.sid.mobile.model.Card;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface CardRepository {
    Card findByNumber(String number);
}
