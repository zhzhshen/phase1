package com.sid.mobile.repository;

import com.sid.mobile.mapper.CardMapper;
import com.sid.mobile.model.Card;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;

@Service
public class CardRepository implements com.sid.mobile.spi.repository.CardRepository {
    @Inject
    CardMapper mapper;

    @Override
    public Card findByNumber(String number) {
        return mapper.findByNumber(number);
    }
}
