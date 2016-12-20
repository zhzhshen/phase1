package util;

import annotation.Column;
import annotation.Entity;
import annotation.Id;
import annotation.Table;
import mapping.ColumnMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectFinderFactory {
    public static <T> ObjectFinder get(Class<T> klass) {
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

        return new ObjectFinder(klass, tableName, columns);
    }
}
