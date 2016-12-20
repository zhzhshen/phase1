package util;

import config.ConnectionConfig;
import mapping.ColumnMapping;
import mapping.Criterion;
import persistence.ConnectionPool;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectFinder {
    private Class klass;
    private ConnectionConfig connectionConfig;
    private final String tableName;
    private final List<ColumnMapping> columns;
    private ColumnMapping id;

    public ObjectFinder(Class klass, ConnectionConfig connectionConfig, String tableName, List<ColumnMapping> columns, ColumnMapping id) {
        this.klass = klass;
        this.connectionConfig = connectionConfig;
        this.tableName = tableName;
        this.columns = columns;
        this.id = id;
    }

    public ColumnMapping getId() {
        return id;
    }

    public <T> T resolve(Criterion... criteria) {
        Connection connection = ConnectionPool.getConnection(connectionConfig);
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(generateSQL(criteria));

            if (rs.next()) {
                return resolveObject(rs);
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateSQL(Criterion... criteria) {
        return "SELECT " + String.join(",", columns.stream().map(ColumnMapping::getColumnName).collect(Collectors.toList()))
                + " FROM " + tableName
                + " WHERE " + String.join(",", Arrays.stream(criteria).map(Criterion::build).collect(Collectors.toList()));
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

    private <T> T resolveObject(ResultSet rs) {
        try {
            T result = (T) klass.newInstance();
            for (ColumnMapping columnMapping : columns) {
                resolveField(result,
                        klass.getField(columnMapping.getFieldName()),
                        rs.getObject(columnMapping.getColumnName()));
            }
            return result;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
