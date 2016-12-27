package com.sid.repository;

import com.sid.mapper.CardMapper;
import com.sid.model.Card;
import com.sid.spi.model.User;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CardRepository implements com.sid.spi.repository.CardRepository {
    @Inject
    CardMapper mapper;

    @Override
    public Card save(Map<String, Object> info, User user) {
        final String id = UUID.randomUUID().toString();
        info.put("id", id);
        info.put("userId", user.getId());
        mapper.save(info);

        return mapper.findById(id);
    }

    @Override
    public List<Card> findByUser(User user) {
        return mapper.findByUserId(user.getId());
    }

    @Override
    public Card findById(String id) {
        return mapper.findById(id);
    }
}
