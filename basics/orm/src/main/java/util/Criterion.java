package util;

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

    public static Criterion eq(String column, Serializable value) {
        return new Criterion(column, "=", value);
    }

    public String build() {
        if (value instanceof String) {
            return column + relation + "'" + value + "'";
        } else {
            return column + relation + value;
        }
    }
}
