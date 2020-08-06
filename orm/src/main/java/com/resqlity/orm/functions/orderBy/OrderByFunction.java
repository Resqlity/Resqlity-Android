package com.resqlity.orm.functions.orderBy;

import com.resqlity.orm.models.clausemodels.OrderByClauseModel;
import com.resqlity.orm.queries.BaseQuery;

public abstract class OrderByFunction {
    protected BaseQuery baseQuery;
    protected OrderByClauseModel orderByClauseModel;

    public OrderByFunction(BaseQuery baseQuery, OrderByClauseModel orderByClauseModel) {
        this.baseQuery = baseQuery;
        this.orderByClauseModel = orderByClauseModel;
    }

    public abstract BaseQuery Query();

    public abstract OrderByFunction ThenBy(Class<?> tableClass, String field, boolean isAsc) throws NoSuchFieldException;

    public abstract OrderByFunction ThenBy(String field, boolean isAsc) throws NoSuchFieldException;

    public void Execute() {
        baseQuery.Execute();
    }

    protected OrderByClauseModel getOrderByClauseModel(String tableName, String tableSchema, String columnName, boolean isAsc) {
        return new OrderByClauseModel(tableName, tableSchema, columnName, isAsc);
    }
}
