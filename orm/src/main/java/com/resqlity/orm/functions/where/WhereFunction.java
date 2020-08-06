package com.resqlity.orm.functions.where;

import com.resqlity.orm.enums.Decision;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.queries.BaseQuery;

public abstract class WhereFunction {
    protected WhereClauseModel model;
    protected BaseQuery baseQuery;
    public WhereFunction(WhereClauseModel root, BaseQuery baseQuery) {
        this.baseQuery = baseQuery;
        model = root;
    }


    public abstract WhereFunction And(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException;
    public abstract WhereFunction Or(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException;
    public abstract WhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException;
    protected abstract WhereFunction Where(String fieldName, Object compareTo, Comparator comparator, Decision decision) throws NoSuchFieldException;
    public abstract BaseQuery Query();
    public void Execute() throws Exception {
        baseQuery.Execute();
    }

    protected WhereClauseModel getWhereClauseModel(String tableName, String tableSchema, String columnName, Object compareTo, Comparator comparator) {
        return new WhereClauseModel(tableName, tableSchema, columnName, compareTo, comparator);
    }


}
