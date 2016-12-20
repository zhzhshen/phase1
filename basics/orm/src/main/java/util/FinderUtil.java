package util;

import config.ConnectionConfig;
import mapping.CriteriaBuilder;
import mapping.Criterion;

import java.io.Serializable;
import java.sql.SQLException;

public class FinderUtil {
    private ConnectionConfig connectionConfig;

    public FinderUtil(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }

    public <T> CriteriaBuilder from(Class<T> klass) {
        return new CriteriaBuilder(this, klass);
    }

    public <T> T get(Class<T> klass, Serializable id)
            throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        return get(klass, Criterion.id(ObjectFinderFactory.get(klass, connectionConfig), id));
    }

    public <T> T get(Class<T> klass, Criterion... criteria)
            throws IllegalAccessException, InstantiationException, SQLException, NoSuchFieldException {
        ObjectFinder objectFinder = ObjectFinderFactory.get(klass, connectionConfig);
        return objectFinder.resolve(criteria);
    }
}
