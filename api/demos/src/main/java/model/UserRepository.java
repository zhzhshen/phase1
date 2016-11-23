package model;

import java.util.Map;

public interface UserRepository {
    User findUserById(Long aLong);

    long create(Map<String, Object> info);
}
