package util;

import config.ConnectionConfig;
import mapping.ColumnMapping;

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

    public String generateSQL(Criterion... criteria) {
        return "SELECT " + String.join(",", columns.stream().map(ColumnMapping::getColumnName).collect(Collectors.toList()))
                + " FROM " + tableName
                + " WHERE " + String.join(",", Arrays.stream(criteria).map(Criterion::build).collect(Collectors.toList()));
    }

    public <T> T resolve(Criterion[] criteria) throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Connection connection = DriverManager.getConnection(connectionConfig.getBaseUrl() + "/" + connectionConfig.getDBName(),
                connectionConfig.getUserName(),
                connectionConfig.getPassword());

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(generateSQL(criteria));

        if (rs.next()) {
            T result = (T) klass.newInstance();
            for (ColumnMapping columnMapping : columns) {
                resolveField(result,
                        klass.getField(columnMapping.getFieldName()),
                        rs.getObject(columnMapping.getColumnName()));
            }
            return result;
        }

        return null;
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
