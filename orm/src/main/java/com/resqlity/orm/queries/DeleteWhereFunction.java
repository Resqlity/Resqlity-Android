package com.resqlity.orm.queries;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.Decision;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.functions.where.WhereFunction;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;

public class DeleteWhereFunction extends WhereFunction {
    DeleteQuery query;

    /**
     * @param root      Root Linked Where Object
     * @param baseQuery DeleteQuery
     */
    public DeleteWhereFunction(WhereClauseModel root, DeleteQuery baseQuery) {
        super(root, baseQuery);
        query = baseQuery;
    }

    /**
     * @param fieldName  Field Name
     * @param compareTo  Compare To Value
     * @param comparator Object Comparator
     * @return DeleteWhereFunction
     * @throws ResqlityDbException
     */
    @Override
    public DeleteWhereFunction And(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        return Where(fieldName, compareTo, comparator, Decision.AND);
    }

    /**
     * @param fieldName  Field Name
     * @param compareTo  Compare To Value
     * @param comparator Object Comparator
     * @return DeleteWhereFunction
     * @throws ResqlityDbException
     */
    @Override
    public DeleteWhereFunction Or(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        return Where(fieldName, compareTo, comparator, Decision.OR);
    }

    /**
     * @param fieldName  Field Name
     * @param compareTo  Compare To Value
     * @param comparator Object Comparator
     * @return DeleteWhereFunction
     * @throws ResqlityDbException
     */
    @Override
    public DeleteWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        query.CompleteWhere();
        return query.Where(fieldName, compareTo, comparator);
    }

    /**
     * @param fieldName  Field Name
     * @param compareTo  Compare To Value
     * @param comparator Object Comparator
     * @param decision   Condition Decision
     * @return DeleteWhereFunction
     * @throws ResqlityDbException
     */
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

    /**
     * @return DeleteQuery
     */
    @Override
    public DeleteQuery Query() {
        return query;
    }

}
