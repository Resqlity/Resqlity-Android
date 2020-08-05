package com.resqlity.orm.models;

public abstract class BaseModel {
    private String apiKey;
    private String tableName;
    private String tableSchema;

    public BaseModel(String apiKey, String tableName, String tableSchema) {
        this.apiKey = apiKey;
        this.tableName = tableName;
        this.tableSchema = tableSchema;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }
}
