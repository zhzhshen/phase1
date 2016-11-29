package repository;

import model.Card;
import model.Topup;

import java.util.List;
import java.util.Map;

public interface TopupRepository {
    List<Topup> findByNumber(String number);

    long create(Card card, Map<String, Object> info);

    Topup findById(long id);
}
