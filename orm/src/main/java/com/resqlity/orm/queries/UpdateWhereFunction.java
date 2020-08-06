package com.resqlity.orm.queries;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.Decision;
import com.resqlity.orm.functions.where.WhereFunction;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;

public class UpdateWhereFunction extends WhereFunction {

    UpdateQuery query;

    public UpdateWhereFunction(WhereClauseModel root, UpdateQuery baseQuery) {
        super(root, baseQuery);
        query = baseQuery;
    }

    @Override
    public UpdateWhereFunction And(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException {
        WhereClauseModel head = model;
        while (head.getInner() != null)
            head = head.getInner();
        head.setDecision(Decision.AND);

        head.setInner(getWhereClauseModel(query.getTableName(), query.getTableSchema(), query.getPropertyName(fieldName), compareTo, comparator));
        model = head;
        return this;
    }

    @Override
    public UpdateWhereFunction Or(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException {
        WhereClauseModel head = model;
        while (head.getInner() != null)
            head = head.getInner();
        head.setDecision(Decision.AND);

        head.setInner(getWhereClauseModel(query.getTableName(), query.getTableSchema(), query.getPropertyName(fieldName), compareTo, comparator));
        model = head;
        return this;
    }

    @Override
    public UpdateWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException {
        query.CompleteWhere();
        return query.Where(fieldName, compareTo, comparator);
    }

    @Override
    public UpdateQuery Query() {
        return query;
    }

    public UpdateQuery Update(String fieldName, Object value) throws Exception {
        query.CompleteWhere();
        return query.Update(fieldName, value);
    }
}
