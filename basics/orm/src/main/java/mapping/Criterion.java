package mapping;

import util.ObjectFinder;

import java.io.Serializable;

public class Criterion {
    private String column;
    private String relation;
    private Serializable value;

    public Criterion(String column, String relation, Serializable value) {
        this.column = column;
        this.relation = relation;
        this.value = value;
    }

    public String build() {
        if (value instanceof String) {
            return column + relation + "'" + value + "'";
        } else {
            return column + relation + value;
        }
    }

    public static Criterion id(ObjectFinder objectFinder, Serializable id) {
        return Criterion.eq(objectFinder.getId().getColumnName(), id);
    }

    public static Criterion eq(String column, Serializable value) {
        return new Criterion(column, "=", value);
    }

    public static Criterion greater(String column, Serializable value) {
        return new Criterion(column, ">", value);
    }

    public static Criterion less(String column, Serializable value) {
        return new Criterion(column, "<", value);
    }
}
