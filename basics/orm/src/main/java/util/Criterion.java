package util;

import mapping.ColumnMapping;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class Criterion {
    private String column;
    private String relation;
    private Serializable value;

    public Criterion(String column, String relation, Serializable value) {
        this.column = column;
        this.relation = relation;
        this.value = value;
    }

    public static Criterion eq(String column, Serializable value) {
        return new Criterion(column, "=", value);
    }

    public static Criterion id(ObjectFinder objectFinder, Serializable id) {
        List<ColumnMapping> ids = objectFinder.getColumns().stream()
                .filter(column -> column.isId())
                .collect(Collectors.toList());
        if (ids.size() > 1) {
            throw new RuntimeException("Class " + objectFinder.getKlass() + " have more than one columns annotated with @Id");
        }
        ColumnMapping idColumn = ids.get(0);
        return Criterion.eq(idColumn.getColumnName(), id);
    }

    public String build() {
        if (value instanceof String) {
            return column + relation + "'" + value + "'";
        } else {
            return column + relation + value;
        }
    }
}
