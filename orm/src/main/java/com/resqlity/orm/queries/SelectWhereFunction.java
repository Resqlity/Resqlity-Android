package com.resqlity.orm.queries;

import com.resqlity.orm.functions.where.WhereFunction;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.Decision;

public class SelectWhereFunction extends WhereFunction {
    SelectQuery query;

    public SelectWhereFunction(WhereClauseModel root, SelectQuery baseQuery) {
        super(root, baseQuery);
        query = baseQuery;
    }

    @Override
    public SelectWhereFunction And(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException {
        WhereClauseModel head = model;
        while (head.getInner() != null)
            head = head.getInner();
        head.setDecision(Decision.AND);

        head.setInner(getWhereClauseModel(query.getTableName(), query.getTableSchema(), query.getPropertyName(fieldName), compareTo, comparator));
        model = head;
        return this;
    }

    @Override
    public SelectWhereFunction Or(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException {
        WhereClauseModel head = model;
        while (head.getInner() != null)
            head = head.getInner();
        head.setDecision(Decision.OR);
        head.setInner(getWhereClauseModel(query.getTableName(), query.getTableSchema(), query.getPropertyName(fieldName), compareTo, comparator));
        model = head;
        return this;
    }


    @Override
    public SelectWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException {
        query.CompleteWhere();
        return query.Where(fieldName, compareTo, comparator);
    }

    @Override
    public SelectQuery Query() {
        return query;
    }

    public SelectQuery Select(String field) throws NoSuchFieldException {
        query.CompleteWhere();
        return query.Select(field);
    }
}
