package spi.repository;

import model.Card;

public interface CardRepository {
    Card findByNumber(String number);
}
