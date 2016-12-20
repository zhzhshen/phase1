package util;

import mapping.ColumnMapping;

import java.util.List;

public class ObjectFinder {
    private Class klass;
    private final String tableName;
    private final List<ColumnMapping> columns;

    public ObjectFinder(Class klass, String tableName, List<ColumnMapping> columns) {
        this.klass = klass;
        this.tableName = tableName;
        this.columns = columns;
    }

    public Class getKlass() {
        return klass;
    }

    public String getTableName() {
        return tableName;
    }

    public List<ColumnMapping> getColumns() {
        return columns;
    }
}
