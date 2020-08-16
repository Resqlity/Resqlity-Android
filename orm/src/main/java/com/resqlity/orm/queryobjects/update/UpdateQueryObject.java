package com.resqlity.orm.queryobjects.update;

import androidx.annotation.Nullable;

/**
 * Update Query Key Value Pairs
 */
public class UpdateQueryObject {
    private String tableName;
    private String tableSchema;
    private String columnName;
    private Object value;

    /**
     * @param tableName   Table Name
     * @param tableSchema Table Schema
     * @param columnName  Column Name
     * @param value       Column Value
     */
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj == this;
    }
}
