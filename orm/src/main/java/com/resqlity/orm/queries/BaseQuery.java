package com.resqlity.orm.queries;

import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.functions.where.WhereFunction;

public abstract class BaseQuery {
    private String tableName;
    private String tableSchema;


    protected WhereClauseModel rootClause;
    public abstract WhereFunction Where(String tableName, String tableSchema, String columnName, Object compareTo, Comparator comparator);
    public abstract void CompleteWhere();
    public abstract void Execute();
}
