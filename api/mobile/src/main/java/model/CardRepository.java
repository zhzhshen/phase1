package model;

public interface CardRepository {
    Card findByNumber(String number);
}
