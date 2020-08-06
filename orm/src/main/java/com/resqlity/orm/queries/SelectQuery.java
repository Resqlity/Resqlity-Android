package com.resqlity.orm.queries;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.functions.orderBy.OrderByFunction;
import com.resqlity.orm.models.clausemodels.OrderByClauseModel;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.models.querymodels.SelectModel;
import com.resqlity.orm.queryobjects.select.SelectColumn;

import java.util.List;

public class SelectQuery extends BaseQuery {
    private SelectModel selectModel;

    public SelectQuery(Class<?> tableClass) {
        super(tableClass);
        selectModel = new SelectModel("", "", "");
    }

    /**
     * @param field Class Field Name
     * @return
     * @throws NoSuchFieldException
     */
    public SelectQuery Select(String field) throws NoSuchFieldException {
        List<SelectColumn> columns = selectModel.getSelectedColumns();
        SelectColumn column = new SelectColumn(super.getTableName(), super.getTableSchema(), super.getPropertyName(field), field);
        columns.add(column);
        selectModel.setSelectedColumns(columns);
        return this;
    }

    public SelectWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException {
        WhereClauseModel root = new WhereClauseModel(super.getTableName(), super.getTableSchema(), super.getPropertyName(fieldName), compareTo, comparator);
        whereRootClause = root;
        return new SelectWhereFunction(root, this);
    }

    @Override
    public SelectOrderByFunction OrderBy(Class<?> tableClass, String field, boolean isAsc) throws NoSuchFieldException {
        if (selectModel.getOrderBy() == null) {
            selectModel.setOrderBy(new OrderByClauseModel(getTableName(tableClass), getTableSchema(tableClass), getPropertyName(field), isAsc));
            return new SelectOrderByFunction(this, selectModel.getOrderBy());
        }
        SelectOrderByFunction func = new SelectOrderByFunction(this, orderByRootClause);
        func.ThenBy(tableClass, field, isAsc);
        return func;
    }

    @Override
    public SelectOrderByFunction OrderBy(String field, boolean isAsc) throws NoSuchFieldException {
        return OrderBy(getBaseTableClass(), field, isAsc);
    }

    protected void CompleteWhere() {
        List<WhereClauseModel> whereClauseModels = selectModel.getWheres();
        whereClauseModels.add(whereRootClause);
        selectModel.setWheres(whereClauseModels);
        whereRootClause = null;
    }
    protected void CompleteOrderBy(){
        selectModel.setOrderBy(orderByRootClause);
    }

    @Override
    public void Execute() {

    }

}
