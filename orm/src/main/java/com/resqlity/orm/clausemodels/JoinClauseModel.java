package com.resqlity.orm.clausemodels;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.JoinType;

import java.util.List;

public class JoinClauseModel {
    private String tableName;
    private String tableSchema;
    private String columnName;
    private String parentColumnName;
    private Comparator comparator;
    private JoinType joinType;
    private List<JoinClauseModel> joins;

    public JoinClauseModel(String tableName,
                           String tableSchema,
                           String columnName,
                           String parentColumnName,
                           Comparator comparator,
                           JoinType joinType) {
        this.tableName = tableName;
        this.tableSchema = tableSchema;
        this.columnName = columnName;
        this.parentColumnName = parentColumnName;
        this.comparator = comparator;
        this.joinType = joinType;
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

    public String getParentColumnName() {
        return parentColumnName;
    }

    public Comparator getComparator() {
        return comparator;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public List<JoinClauseModel> getJoins() {
        return joins;
    }

    public void setJoins(List<JoinClauseModel> joins) {
        this.joins = joins;
    }
}
