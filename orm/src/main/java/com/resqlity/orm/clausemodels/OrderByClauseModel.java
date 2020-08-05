package com.resqlity.orm.clausemodels;

public class OrderByClauseModel {
    private String tableName;
    private String tableSchema;
    private String columnName;
    private boolean isAsc;
    private OrderByClauseModel thenBy;

    public OrderByClauseModel(String tableName, String tableSchema, String columnName, boolean isAsc) {
        this.tableName = tableName;
        this.tableSchema = tableSchema;
        this.columnName = columnName;
        this.isAsc = isAsc;
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

    public boolean isAsc() {
        return isAsc;
    }

    public OrderByClauseModel getThenBy() {
        return thenBy;
    }

    public void setThenBy(OrderByClauseModel thenBy) {
        this.thenBy = thenBy;
    }
}
