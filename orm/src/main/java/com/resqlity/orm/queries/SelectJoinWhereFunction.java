package com.resqlity.orm.queries;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.Decision;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.functions.where.WhereFunction;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;

public class SelectJoinWhereFunction extends WhereFunction {

    SelectQuery query;
    SelectJoinFunction function;

    public SelectJoinWhereFunction(WhereClauseModel root, SelectQuery baseQuery, SelectJoinFunction function) {
        super(root, baseQuery);
        this.function = function;
        query=baseQuery;
    }


    @Override
    public SelectJoinWhereFunction And(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        return Where(fieldName, compareTo, comparator, Decision.AND);
    }

    @Override
    public SelectJoinWhereFunction Or(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        return Where(fieldName, compareTo, comparator, Decision.OR);
    }

    @Override
    public SelectJoinWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        function.CompleteWhere();
        return function.Where(fieldName, compareTo, comparator);
    }

    @Override
    protected SelectJoinWhereFunction Where(String fieldName, Object compareTo, Comparator comparator, Decision decision) throws ResqlityDbException {
        WhereClauseModel head = model;
        while (head.getInner() != null)
            head = head.getInner();
        head.setDecision(decision);
        WhereClauseModel clauseModel = getWhereClauseModel(query.getTableName(function.getBaseClass()),
                query.getTableSchema(function.getBaseClass()),
                query.getPropertyName(function.getBaseClass(), fieldName),
                compareTo,
                comparator);
        head.setInner(clauseModel);
        model = head;
        return this;
    }

    public SelectJoinFunction Parent() {
        function.CompleteWhere();
        return function;
    }

    @Override
    @Deprecated
    public SelectQuery Query() {
        throw new UnsupportedOperationException();
    }
}
