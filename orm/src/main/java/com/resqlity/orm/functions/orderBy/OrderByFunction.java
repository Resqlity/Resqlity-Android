package com.resqlity.orm.functions.orderBy;

import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.models.clausemodels.OrderByClauseModel;
import com.resqlity.orm.queries.BaseFilterableQuery;

/**
 * Order By Function
 */
public abstract class OrderByFunction {
    /**
     * Base Filterable Query
     */
    protected BaseFilterableQuery baseQuery;
    /**
     * Order By Linked Object
     */
    protected OrderByClauseModel orderByClauseModel;

    public OrderByFunction(BaseFilterableQuery baseQuery, OrderByClauseModel orderByClauseModel) {
        this.baseQuery = baseQuery;
        this.orderByClauseModel = orderByClauseModel;
    }

    /**
     * @return BaseFilterableQuery
     */
    public abstract BaseFilterableQuery Query();

    /**
     * @param tableClass Table Class
     * @param field      Field to OrderBy
     * @param isAsc      Is Ascending
     * @return OrderByFunction
     * @throws ResqlityDbException
     */
    public abstract OrderByFunction ThenBy(Class<?> tableClass, String field, boolean isAsc) throws ResqlityDbException;

    /**
     * @param field Field to OrderBy
     * @param isAsc Is Ascending
     * @return OrderByFunction
     * @throws ResqlityDbException
     */
    public abstract OrderByFunction ThenBy(String field, boolean isAsc) throws ResqlityDbException;

    /**
     * @param tableName   Table Name
     * @param tableSchema Table Schema
     * @param columnName  Column Name
     * @param isAsc       Is Ascending
     * @return New OrderByClauseModel
     */
    protected OrderByClauseModel getOrderByClauseModel(String tableName, String tableSchema, String columnName, boolean isAsc) {
        return new OrderByClauseModel(tableName, tableSchema, columnName, isAsc);
    }
}
