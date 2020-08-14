package com.resqlity.orm.queries;

import com.resqlity.orm.exceptions.ResqlityDbException;
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

    /**
     * @param fieldName Field Name To Apply Condition
     * @param compareTo Value To Compare
     * @param comparator Comparator (such as Comparator.Equal,Comparator.NotEqual)
     * @return SelectWhereFunction
     * @throws ResqlityDbException
     */
    @Override
    public SelectWhereFunction And(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        return Where(fieldName,compareTo,comparator,Decision.AND);
    }

    /**
     * @param fieldName Field Name To Apply Condition
     * @param compareTo Value To Compare
     * @param comparator Comparator (such as Comparator.Equal,Comparator.NotEqual)
     * @return SelectWhereFunction
     * @throws ResqlityDbException
     */
    @Override
    public SelectWhereFunction Or(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        return Where(fieldName,compareTo,comparator,Decision.OR);
    }

    /**
     * @param fieldName Field Name To Apply Condition
     * @param compareTo Value To Compare
     * @param comparator Comparator (such as Comparator.Equal,Comparator.NotEqual)
     * @return SelectWhereFunction
     * @throws ResqlityDbException
     */
    @Override
    public SelectWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        query.CompleteWhere();
        return query.Where(fieldName, compareTo, comparator);
    }

    @Override
    protected SelectWhereFunction Where(String fieldName, Object compareTo, Comparator comparator, Decision decision) throws ResqlityDbException {
        WhereClauseModel head = model;
        while (head.getInner() != null)
            head = head.getInner();
        head.setDecision(decision);
        head.setInner(getWhereClauseModel(query.getTableName(), query.getTableSchema(), query.getPropertyName(fieldName), compareTo, comparator));
        model = head;
        return this;
    }

    @Override
    public SelectQuery Query() {
        return query;
    }

    public SelectQuery Select(String field) throws ResqlityDbException {
        query.CompleteWhere();
        return query.Select(field);
    }
}
