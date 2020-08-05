package com.resqlity.orm.clausemodels;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.Decision;
import com.resqlity.orm.models.BaseModel;

public class WhereClauseModel {
    private String tableName;
    private String tableSchema;
    private String columnName;
    private Object compareTo;
    private Comparator comparator;
    private Decision decision;
    private WhereClauseModel inner;

    public WhereClauseModel(String tableName, String tableSchema, String columnName, Object compareTo, Comparator comparator) {
        this.tableName = tableName;
        this.tableSchema = tableSchema;
        this.columnName = columnName;
        this.compareTo = compareTo;
        this.comparator = comparator;
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

    public Object getCompareTo() {
        return compareTo;
    }

    public Comparator getComparator() {
        return comparator;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public WhereClauseModel getInner() {
        return inner;
    }

    public void setInner(WhereClauseModel inner) {
        this.inner = inner;
    }
}
