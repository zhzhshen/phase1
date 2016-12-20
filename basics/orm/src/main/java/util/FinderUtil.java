package util;

import annotation.Column;
import annotation.Entity;
import annotation.Id;
import annotation.Table;
import config.ConnectionConfig;
import mapping.ColumnMapping;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class FinderUtil {
    private ConnectionConfig connectionConfig;

    public FinderUtil(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }

    public <T> T get(Class<T> klass, Serializable id) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Connection connection = DriverManager.getConnection(connectionConfig.getBaseUrl() + "/" + connectionConfig.getDBName(),
                connectionConfig.getUserName(),
                connectionConfig.getPassword());

        if (!klass.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("Class " + klass.getName() + " should be annotated with @Entity in order to be persistable");
        }

        String tableName = klass.isAnnotationPresent(Table.class)
                ? klass.getAnnotation(Table.class).name()
                : klass.getName();

        List<ColumnMapping> columns = Arrays.stream(klass.getFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                    boolean isId = field.isAnnotationPresent(Id.class);
                    String columnName = field.getAnnotation(Column.class).name().isEmpty()
                            ? field.getName()
                            : field.getAnnotation(Column.class).name();
                    return new ColumnMapping(field.getName(), columnName, isId);
                })
                .collect(Collectors.toList());

        List<ColumnMapping> ids = columns.stream().filter(column -> column.isId()).collect(Collectors.toList());
        if (ids.size() > 1) {
            throw new RuntimeException("Class "+ klass + " have more than one columns annotated with @Id");
        }
        ColumnMapping idColumn = ids.get(0);

        Statement stmt = connection.createStatement();
        String sql = "SELECT " + String.join(",", columns.stream().map(column -> column.getColumnName()).collect(Collectors.toList())) + " FROM " + tableName + " where " + idColumn.getColumnName() + "=" + id;

        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            T result = klass.newInstance();
            for (ColumnMapping columnMapping : columns) {
                Object value = rs.getObject(columnMapping.getColumnName());

                Field field = klass.getField(columnMapping.getFieldName());
                resolveField(result, field, value);
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
