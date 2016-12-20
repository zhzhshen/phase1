package mapping;

public class ColumnMapping {
    private String fieldName;
    private String columnName;
    private boolean isId;

    public ColumnMapping(String fieldName, String columnName, boolean isId) {
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.isId = isId;
    }

    public boolean isId() {
        return isId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
    }
}
