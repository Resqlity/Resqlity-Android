package com.resqlity.orm.models.mappingmodels;

public class EntityTableMapping {
    private String tableName;
    private String tableSchema;
    private String className;

    public EntityTableMapping(String tableName, String tableSchema, String className) {
        this.tableName = tableName;
        this.tableSchema = tableSchema;
        this.className = className;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
