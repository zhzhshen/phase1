package util;

import config.ConnectionConfig;
import mapping.ColumnMapping;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        return get(klass, Criterion.id(ObjectFinderFactory.get(klass), id));
    }

    public <T> T get(Class<T> klass, Criterion... criteria)
            throws IllegalAccessException, InstantiationException, SQLException, NoSuchFieldException {
        ObjectFinder objectFinder = ObjectFinderFactory.get(klass);

        Connection connection = DriverManager.getConnection(connectionConfig.getBaseUrl() + "/" + connectionConfig.getDBName(),
                connectionConfig.getUserName(),
                connectionConfig.getPassword());
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(generateSql(objectFinder, criteria));

        if (rs.next()) {
            T result = klass.newInstance();
            for (ColumnMapping columnMapping : objectFinder.getColumns()) {
                Object value = rs.getObject(columnMapping.getColumnName());

                Field field = klass.getField(columnMapping.getFieldName());
                resolveField(result, field, value);
            }
            return result;
        }

        return null;
    }

    public String generateSql(ObjectFinder objectFinder, Criterion[] criteria) {
        List<ColumnMapping> columns = objectFinder.getColumns();
        return "SELECT " + String.join(",", columns.stream().map(column -> column.getColumnName()).collect(Collectors.toList()))
                + " FROM " + objectFinder.getTableName()
                + " WHERE " + String.join(",", Arrays.stream(criteria).map(criterion -> criterion.build()).collect(Collectors.toList()));
    }

    private <T> void resolveField(T result, Field field, Object value) {
        field.setAccessible(true);

        try {
            field.set(result, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        field.setAccessible(false);
    }
}
