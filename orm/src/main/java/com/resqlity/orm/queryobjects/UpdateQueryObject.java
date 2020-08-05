package com.resqlity.orm.queryobjects;

public class UpdateQueryObject {
    private String tableName;
    private String tableSchema;
    private String columnName;
    private Object value;

    public UpdateQueryObject(String tableName, String tableSchema, String columnName, Object value) {
        this.tableName = tableName;
        this.tableSchema = tableSchema;
        this.columnName = columnName;
        this.value = value;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public String getColumnName() {
        return columnName;
    }

    public Object getValue() {
        return value;
    }
}
