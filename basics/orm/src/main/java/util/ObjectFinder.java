package util;

import annotation.OneToOne;
import config.ConnectionConfig;
import mapping.ColumnMapping;
import mapping.Criterion;
import persistence.ConnectionPool;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectFinder {
    private Class klass;
    private final String tableName;
    private final List<ColumnMapping> columns;
    private ColumnMapping id;
    private Connection connection;
    private ConnectionConfig connectionConfig;

    public ObjectFinder(Class klass, ConnectionConfig connectionConfig, String tableName, List<ColumnMapping> columns, ColumnMapping id) {
        this.klass = klass;
        this.tableName = tableName;
        this.columns = columns;
        this.id = id;
        this.connectionConfig = connectionConfig;
        this.connection = ConnectionPool.getConnection(connectionConfig);
    }

    public ColumnMapping getIdColumn() {
        return id;
    }

    public <T> T get(Criterion... criteria) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(getSQLRead(criteria));

            if (rs.next()) {
                return getObject(rs);
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

    public <T> void save(T object) throws SQLException {
        saveChildren(object);
        connection.createStatement().executeUpdate(getSQLWrite(object));
        connection.close();
    }

    private <T> void saveChildren(T object) throws SQLException {
        List<Field> oneToOnes = Arrays.stream(object.getClass().getFields()).filter(field -> field.isAnnotationPresent(OneToOne.class)).collect(Collectors.toList());

        for (Field oneToOne : oneToOnes) {
            oneToOne.setAccessible(true);
            Object objectToSave;
            try {
                objectToSave = oneToOne.get(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot access to field " + klass + ":" + oneToOne.getName());
            }
            ObjectFinderFactory.get(objectToSave.getClass(), connectionConfig).save(objectToSave);
        }
    }

    public <T> void update(T object) throws SQLException {
        connection.createStatement().executeUpdate(getSQLUpdate(object));
        connection.close();
    }

    public <T> void delete(T object) throws SQLException {
        connection.createStatement().executeUpdate(getSQLDelete(object));
        connection.close();
    }

    private String getSQLRead(Criterion... criteria) {
        return "SELECT " + String.join(",", columns.stream().map(ColumnMapping::getColumnName).collect(Collectors.toList()))
                + " FROM " + tableName
                + " WHERE " + String.join(",", Arrays.stream(criteria).map(Criterion::build).collect(Collectors.toList()));
    }

    private <T> String getSQLWrite(T object) {
        return "INSERT INTO " + tableName + "("
                + String.join(",", columns.stream().map(ColumnMapping::getColumnName).collect(Collectors.toList())) + ")"
                + " VALUES("
                + String.join(",",
                    columns.stream()
                        .map(column -> {
                            try {
                                Field field = object.getClass().getDeclaredField(column.getFieldName());
                                field.setAccessible(true);
                                if (String.class == field.getType()) {
                                    return "'" + field.get(object) + "'";
                                } else {
                                    return String.valueOf(field.get(object));
                                }
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(Collectors.toList()))
                + ")";
    }

    private <T> String getSQLUpdate(T object) {
        return "UPDATE " + tableName
                + " SET "
                + String.join(",",
                columns.stream()
                        .filter(column -> !column.isId())
                        .map(column -> column.getColumnName() + "=" + getFieldValue(column, object))
                        .collect(Collectors.toList()))
                + " WHERE "
                + id.getColumnName() + "=" + getFieldValue(id, object);
    }

    private <T> String getSQLDelete(T object) {
        return "DELETE FROM " + tableName
                + " WHERE "
                + id.getColumnName() + "=" + getFieldValue(id, object);
    }

    private <T> String getFieldValue(ColumnMapping column, T object) {
        String value;
        try {
            Field field = object.getClass().getDeclaredField(column.getFieldName());
            field.setAccessible(true);
            if (String.class == field.getType()) {
                value = "'" + field.get(object) + "'";
            } else {
                value = String.valueOf(field.get(object));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    private <T> void injectField(T result, Field field, Object value) {
        field.setAccessible(true);

        try {
            field.set(result, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        field.setAccessible(false);
    }

    private <T> T getObject(ResultSet rs) {
        try {
            T result = (T) klass.newInstance();
            for (ColumnMapping columnMapping : columns) {
                injectField(result,
                        klass.getField(columnMapping.getFieldName()),
                        rs.getObject(columnMapping.getColumnName()));
            }
            return result;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
