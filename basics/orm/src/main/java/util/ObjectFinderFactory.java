package util;

import annotation.Column;
import annotation.Entity;
import annotation.Id;
import annotation.Table;
import config.ConnectionConfig;
import mapping.ColumnMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectFinderFactory {
    public static <T> ObjectFinder get(Class<T> klass, ConnectionConfig connectionConfig) {
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

        List<ColumnMapping> ids = columns.stream()
                .filter(column -> column.isId())
                .collect(Collectors.toList());
        if (ids.size() > 1) {
            throw new RuntimeException("Class " + klass + " have more than one columns annotated with @Id");
        }

        return new ObjectFinder(klass, connectionConfig, tableName, columns, ids.get(0));
    }
}
