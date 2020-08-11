package com.resqlity.orm.queries;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.Decision;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.functions.where.WhereFunction;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;

public class DeleteWhereFunction extends WhereFunction {
    DeleteQuery query;

    public DeleteWhereFunction(WhereClauseModel root, DeleteQuery baseQuery) {
        super(root, baseQuery);
        query = baseQuery;
    }

    @Override
    public DeleteWhereFunction And(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        return Where(fieldName, compareTo, comparator, Decision.AND);
    }

    @Override
    public DeleteWhereFunction Or(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        return Where(fieldName, compareTo, comparator, Decision.OR);
    }

    @Override
    public DeleteWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        query.CompleteWhere();
        return query.Where(fieldName, compareTo, comparator);
    }

    @Override
    protected DeleteWhereFunction Where(String fieldName, Object compareTo, Comparator comparator, Decision decision) throws ResqlityDbException {
        WhereClauseModel head = model;
        while (head.getInner() != null)
            head = head.getInner();
        head.setDecision(decision);

        head.setInner(getWhereClauseModel(query.getTableName(), query.getTableSchema(), query.getPropertyName(fieldName), compareTo, comparator));
        model = head;
        return this;
    }

    @Override
    public DeleteQuery Query() {
        return query;
    }

}
