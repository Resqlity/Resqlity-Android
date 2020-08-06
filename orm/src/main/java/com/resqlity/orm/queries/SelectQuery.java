package com.resqlity.orm.queries;

import com.resqlity.orm.enums.Comparator;
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
        rootClause = root;
        return new SelectWhereFunction(root, this);
    }

    protected void CompleteWhere() {
        List<WhereClauseModel> whereClauseModels = selectModel.getWheres();
        whereClauseModels.add(rootClause);
        selectModel.setWheres(whereClauseModels);
        rootClause = null;
    }

    @Override
    public void Execute() {

    }

}
