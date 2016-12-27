package com.sid.mapper;

import com.sid.model.Card;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CardMapper {
    void save(@Param("info") Map<String, Object> info);

    List<Card> findByUserId(@Param("userId") String id);

    Card findById(@Param("id") String id);
}
