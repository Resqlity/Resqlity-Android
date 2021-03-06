package com.resqlity.orm.queries;

import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.functions.orderBy.OrderByFunction;
import com.resqlity.orm.models.clausemodels.OrderByClauseModel;

public class SelectOrderByFunction extends OrderByFunction {
    SelectQuery query;

    /**
     * @param baseQuery          SelectQuery
     * @param orderByClauseModel Linked Order By Object
     */
    public SelectOrderByFunction(SelectQuery baseQuery, OrderByClauseModel orderByClauseModel) {
        super(baseQuery, orderByClauseModel);
        query = baseQuery;
    }

    /**
     * @return SelectQuery
     */
    @Override
    public SelectQuery Query() {
        query.CompleteOrderBy();
        return query;
    }

    /**
     * @param tableClass Table Class
     * @param field      Field to OrderBy
     * @param isAsc      Is Ascending
     * @return SelectOrderByFunction
     * @throws ResqlityDbException
     */
    @Override
    public SelectOrderByFunction ThenBy(Class<?> tableClass, String field, boolean isAsc) throws ResqlityDbException {
        OrderByClauseModel head = orderByClauseModel;
        while (head.getThenBy() != null)
            head = head.getThenBy();
        head.setThenBy(getOrderByClauseModel(baseQuery.getTableName(tableClass), baseQuery.getTableSchema(tableClass), baseQuery.getPropertyName(tableClass, field), isAsc));
        orderByClauseModel = head;
        return this;
    }

    /**
     * @param field Field to OrderBy
     * @param isAsc Is Ascending
     * @return SelectOrderByFunction
     * @throws ResqlityDbException
     */
    @Override
    public SelectOrderByFunction ThenBy(String field, boolean isAsc) throws ResqlityDbException {
        return ThenBy(query.getBaseTableClass(), field, isAsc);
    }
}
