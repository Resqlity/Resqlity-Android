package com.resqlity.orm.queries.select;

import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.functions.where.SelectWhereFunction;
import com.resqlity.orm.models.querymodels.SelectModel;
import com.resqlity.orm.queries.BaseQuery;
import com.resqlity.orm.queryobjects.select.SelectColumn;

import java.util.List;

public class SelectQuery extends BaseQuery {
    private SelectModel selectModel;

    public SelectQuery() {
        selectModel=new SelectModel("","","");
    }

    public SelectQuery Select(SelectColumn column){
        List<SelectColumn> columns=selectModel.getSelectedColumns();
        columns.add(column);
        selectModel.setSelectedColumns(columns);
        return this;
    }
    public SelectWhereFunction Where(String tableName, String tableSchema, String columnName, Object compareTo, Comparator comparator)
    {
        WhereClauseModel root=new WhereClauseModel(tableName,tableSchema,columnName,compareTo,comparator);
        rootClause=root;
        return new SelectWhereFunction(root,this);
    }
    public void CompleteWhere(){
        List<WhereClauseModel> whereClauseModels=selectModel.getWheres();
        whereClauseModels.add(rootClause);
        selectModel.setWheres(whereClauseModels);
        rootClause=null;
    }

    @Override
    public void Execute() {

    }

}
