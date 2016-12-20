package util;

import config.ConnectionConfig;
import mapping.CriteriaBuilder;
import mapping.Criterion;

import java.io.Serializable;

public class MappingUtil {
    private ConnectionConfig connectionConfig;

    public MappingUtil(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }

    public <T> CriteriaBuilder from(Class<T> klass) {
        return new CriteriaBuilder(this, klass);
    }

    public <T> T get(Class<T> klass, Serializable id) {
        return get(klass, Criterion.id(ObjectFinderFactory.get(klass, connectionConfig), id));
    }

    public <T> T get(Class<T> klass, Criterion... criteria) {
        ObjectFinder objectFinder = ObjectFinderFactory.get(klass, connectionConfig);
        return objectFinder.get(criteria);
    }

    public <T> void save(T object) {
        ObjectFinder objectFinder = ObjectFinderFactory.get(object.getClass(), connectionConfig);
        objectFinder.save(object);
    }
}
