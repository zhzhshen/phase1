package com.sid.mobile.mapper;

import com.sid.mobile.model.Card;
import org.apache.ibatis.annotations.Param;

public interface CardMapper {
    Card findByNumber(@Param("number") String number);
}
