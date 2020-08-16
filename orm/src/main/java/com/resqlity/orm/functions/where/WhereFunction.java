package com.resqlity.orm.functions.where;

import com.resqlity.orm.enums.Decision;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.queries.BaseFilterableQuery;

/**
 * Where Function
 */
public abstract class WhereFunction {
    /**
     * Linked Where Clause Object
     */
    protected WhereClauseModel model;
    /**
     * BaseFilterableQuery
     */
    protected BaseFilterableQuery baseQuery;

    /**
     * @param root      Root Linked Where Object
     * @param baseQuery Base Query
     */
    public WhereFunction(WhereClauseModel root, BaseFilterableQuery baseQuery) {
        this.baseQuery = baseQuery;
        model = root;
    }


    /**
     * @param fieldName  Field Name
     * @param compareTo  Compare To Value
     * @param comparator Object Comparator
     * @return WhereFunction
     * @throws ResqlityDbException
     */
    public abstract WhereFunction And(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException;

    /**
     * @param fieldName  Field Name
     * @param compareTo  Compare To Value
     * @param comparator Object Comparator
     * @return WhereFunction
     * @throws ResqlityDbException
     */
    public abstract WhereFunction Or(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException;

    /**
     * @param fieldName  Field Name
     * @param compareTo  Compare To Value
     * @param comparator Object Comparator
     * @return WhereFunction
     * @throws ResqlityDbException
     */
    public abstract WhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException;


    /**
     * @param fieldName  Field Name
     * @param compareTo  Compare To Value
     * @param comparator Object Comparator
     * @param decision   Condition Decision
     * @return WhereFunction
     * @throws ResqlityDbException
     */
    protected abstract WhereFunction Where(String fieldName, Object compareTo, Comparator comparator, Decision decision) throws ResqlityDbException;

    /**
     * @return BaseFilterableQuery
     */
    public abstract BaseFilterableQuery Query();

    /**
     * @param tableName   Table Name
     * @param tableSchema Table Schema
     * @param columnName  Column Name
     * @param compareTo   Compare To Value
     * @param comparator  Object Comparator
     * @return New WhereClauseModel
     */
    protected WhereClauseModel getWhereClauseModel(String tableName, String tableSchema, String columnName, Object compareTo, Comparator comparator) {
        return new WhereClauseModel(tableName, tableSchema, columnName, compareTo, comparator);
    }


}
