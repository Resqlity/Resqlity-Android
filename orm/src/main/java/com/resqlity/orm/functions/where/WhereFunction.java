package com.resqlity.orm.functions.where;

import com.resqlity.orm.clausemodels.WhereClauseModel;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.queries.BaseQuery;

public abstract class WhereFunction {
    protected WhereClauseModel model;
    protected BaseQuery baseQuery;
    public WhereFunction(WhereClauseModel root, BaseQuery baseQuery) {
        this.baseQuery = baseQuery;
        model = root;
    }


    public abstract WhereFunction And(String tableName, String tableSchema, String columnName, Object compareTo, Comparator comparator);
    public abstract WhereFunction Or(String tableName, String tableSchema, String columnName, Object compareTo, Comparator comparator);
    public abstract WhereFunction Where(String tableName, String tableSchema, String columnName, Object compareTo, Comparator comparator);


    public void Execute(){
        baseQuery.Execute();
    }

    protected WhereClauseModel getWhereClauseModel(String tableName, String tableSchema, String columnName, Object compareTo, Comparator comparator) {
        return new WhereClauseModel(tableName, tableSchema, columnName, compareTo, comparator);
    }


}
