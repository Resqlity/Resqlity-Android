package com.resqlity.orm.functions.where;

import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.Decision;
import com.resqlity.orm.queries.select.SelectQuery;
import com.resqlity.orm.queryobjects.select.SelectColumn;

public class SelectWhereFunction extends WhereFunction {
    SelectQuery query;

    public SelectWhereFunction(WhereClauseModel root, SelectQuery baseQuery) {
        super(root, baseQuery);
        query=baseQuery;
    }

    @Override
    public SelectWhereFunction And(String tableName, String tableSchema, String columnName, Object compareTo, Comparator comparator) {
        WhereClauseModel head = model;
        while (head.getInner() != null)
            head = head.getInner();
        head.setDecision(Decision.AND);
        head.setInner(getWhereClauseModel(tableName, tableSchema, columnName, compareTo, comparator));
        model=head;
        return this;
    }

    @Override
    public SelectWhereFunction Or(String tableName, String tableSchema, String columnName, Object compareTo, Comparator comparator) {
        WhereClauseModel head = model;
        while (head.getInner() != null)
            head = head.getInner();
        head.setDecision(Decision.OR);
        head.setInner(getWhereClauseModel(tableName, tableSchema, columnName, compareTo, comparator));
        model=head;
        return this;
    }


    @Override
    public SelectWhereFunction Where(String tableName, String tableSchema, String columnName, Object compareTo, Comparator comparator) {
        query.CompleteWhere();
        return query.Where(tableName, tableSchema, columnName, compareTo, comparator);
    }

    public SelectQuery Select(SelectColumn column) {
        query.CompleteWhere();
        return query.Select(column);
    }
}
