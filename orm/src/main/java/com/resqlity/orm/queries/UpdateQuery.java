package com.resqlity.orm.queries;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.functions.join.JoinFunction;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.models.querymodels.UpdateModel;
import com.resqlity.orm.queryobjects.update.UpdateQueryObject;

import java.util.List;

public class UpdateQuery extends BaseQuery {
    UpdateModel updateModel;

    public UpdateQuery(Class<?> tableClass) {
        super(tableClass);
        updateModel = new UpdateModel();
    }

    public UpdateQuery Update(String fieldName, Object value) throws Exception {
        List<UpdateQueryObject> updateQueryObjects = updateModel.getModel();
        String columnName = getPropertyName(fieldName);
        String tableName = getTableName();
        String tableSchema = getTableSchema();
        UpdateQueryObject object = new UpdateQueryObject(tableName, tableSchema, columnName, value);
        if (updateQueryObjects.stream().anyMatch(x -> x.getColumnName() == columnName && x.getTableName() == tableName && x.getTableSchema() == tableSchema))
            throw new Exception("Key already exists");
        updateQueryObjects.add(object);
        updateModel.setModel(updateQueryObjects);
        return this;
    }

    @Override
    public UpdateWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException {
        WhereClauseModel root = new WhereClauseModel(super.getTableName(), super.getTableSchema(), super.getPropertyName(fieldName), compareTo, comparator);
        whereRootClause = root;
        return new UpdateWhereFunction(root, this);
    }

    @Override
    public JoinFunction InnerJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public JoinFunction LeftJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public JoinFunction RightJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public JoinFunction LeftOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public JoinFunction RightOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    protected void CompleteWhere() {
        List<WhereClauseModel> whereClauseModels = updateModel.getWheres();
        whereClauseModels.add(whereRootClause);
        updateModel.setWheres(whereClauseModels);
        whereRootClause = null;
    }

    @Override
    protected void CompleteJoin() throws NoSuchMethodException {
        throw new NoSuchMethodException("Not Implemented");
    }

    @Override
    public void Execute() throws Exception {
        if (whereRootClause != null)
            CompleteWhere();

        if (updateModel.getModel() == null || updateModel.getModel().isEmpty())
            throw new Exception("Invalid Operation");
    }

    public void Execute(boolean useTransaction) throws Exception {
        updateModel.setUseTransaction(useTransaction);
        Execute();
    }
}
