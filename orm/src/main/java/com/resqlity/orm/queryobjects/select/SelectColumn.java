package com.resqlity.orm.queryobjects.select;

import androidx.annotation.Nullable;

public class SelectColumn {
    private String tableName;
    private String tableSchema;
    private String columnName;
    private String columnAlias;

    public SelectColumn(String tableName, String tableSchema, String columnName, String columnAlias) {
        this.tableName = tableName;
        this.tableSchema = tableSchema;
        this.columnName = columnName;
        this.columnAlias = columnAlias;
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

    public String getColumnAlias() {
        return columnAlias;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj==this;
    }
}
