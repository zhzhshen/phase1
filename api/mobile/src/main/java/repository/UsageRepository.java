package repository;

import model.Card;
import model.Usage;

import java.util.List;
import java.util.Map;

public interface UsageRepository {
    long create(Card card, Map<String, Object> info);

    Usage findById(long id);

    List<Usage> findByNumber(String number);
}
