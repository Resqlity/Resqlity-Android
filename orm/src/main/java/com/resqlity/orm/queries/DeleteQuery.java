package com.resqlity.orm.queries;

import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.functions.join.JoinFunction;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.models.querymodels.DeleteModel;

import java.util.List;

public class DeleteQuery extends BaseFilterableQuery {
    DeleteModel deleteModel;

    public DeleteQuery(Class<?> tableClass, ResqlityContext dbContext) {
        super(tableClass, dbContext);
        deleteModel = new DeleteModel(dbContext.getApiKey(), getTableName(), getTableSchema());
    }

    @Override
    public DeleteWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException {
        WhereClauseModel root = new WhereClauseModel(super.getTableName(), super.getTableSchema(), super.getPropertyName(fieldName), compareTo, comparator);
        whereRootClause = root;
        return new DeleteWhereFunction(root, this);
    }

    @Override
    public JoinFunction InnerJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException();
    }

    @Override
    public JoinFunction LeftJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException();
    }

    @Override
    public JoinFunction RightJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException();
    }

    @Override
    public JoinFunction LeftOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException();
    }

    @Override
    public JoinFunction RightOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException, NoSuchMethodException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void CompleteWhere() {
        List<WhereClauseModel> whereClauseModels = deleteModel.getWheres();
        whereClauseModels.add(whereRootClause);
        deleteModel.setWheres(whereClauseModels);
        whereRootClause = null;
    }

    @Override
    protected void CompleteJoin() throws NoSuchMethodException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void Execute() throws Exception {
        if (whereRootClause != null)
            CompleteWhere();
    }

    public void Execute(boolean useTransaction) throws Exception {
        deleteModel.setUseTransaction(useTransaction);
        Execute();
    }
}
