package com.resqlity.orm.queries;

import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.consts.Pagination;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.JoinType;
import com.resqlity.orm.models.clausemodels.JoinClauseModel;
import com.resqlity.orm.models.clausemodels.OrderByClauseModel;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.models.querymodels.SelectModel;
import com.resqlity.orm.queryobjects.select.SelectColumn;

import java.util.List;

public class SelectQuery extends BaseFilterableQuery {
    private SelectModel selectModel;

    public SelectQuery(Class<?> tableClass, ResqlityContext dbContext) {
        super(tableClass, dbContext);
        selectModel = new SelectModel(dbContext.getApiKey(), getTableName(), getTableSchema());
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

    public SelectOrderByFunction OrderBy(Class<?> tableClass, String field, boolean isAsc) throws NoSuchFieldException {
        if (selectModel.getOrderBy() == null) {
            selectModel.setOrderBy(new OrderByClauseModel(getTableName(tableClass), getTableSchema(tableClass), getPropertyName(field), isAsc));
            return new SelectOrderByFunction(this, selectModel.getOrderBy());
        }
        SelectOrderByFunction func = new SelectOrderByFunction(this, selectModel.getOrderBy());
        func.ThenBy(tableClass, field, isAsc);
        return func;
    }

    public SelectOrderByFunction OrderBy(String field, boolean isAsc) throws NoSuchFieldException {
        return OrderBy(getBaseTableClass(), field, isAsc);
    }

    @Override
    public SelectJoinFunction InnerJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.INNER);
    }

    @Override
    public SelectJoinFunction LeftJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.LEFT);
    }

    @Override
    public SelectJoinFunction RightJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.RIGHT);
    }

    @Override
    public SelectJoinFunction LeftOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.LEFT_OUTER);
    }

    @Override
    public SelectJoinFunction RightOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.RIGHT_OUTER);
    }

    private SelectJoinFunction Join(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator, JoinType type) throws NoSuchFieldError, NoSuchFieldException {
        String tableName = getTableName(joinClass);
        String tableSchema = getTableSchema(joinClass);
        String parentColumnName = getPropertyName(parentFieldName);
        String childColumnName = getPropertyName(joinClass, fieldName);
        JoinClauseModel joinClauseModel = new JoinClauseModel(tableName, tableSchema, childColumnName, parentColumnName, comparator, type);
        lastJoinClause = joinClauseModel;
        return new SelectJoinFunction(this, joinClauseModel, joinClass);
    }


    public SelectQuery PageBy() {
        return PageBy(1);
    }

    public SelectQuery PageBy(int page) {
        return PageBy((page - 1) * Pagination.PageSize, Pagination.PageSize);
    }

    public SelectQuery PageBy(int page, long pageSize) {
        return PageBy((page - 1) * pageSize, pageSize);
    }

    public SelectQuery PageBy(long skipCount, long maxResultCount) {
        selectModel.setMaxResultCount(maxResultCount);
        selectModel.setSkipCount(skipCount);
        return this;
    }


    protected void CompleteWhere() {
        List<WhereClauseModel> whereClauseModels = selectModel.getWheres();
        whereClauseModels.add(whereRootClause);
        selectModel.setWheres(whereClauseModels);
        whereRootClause = null;
    }

    protected void CompleteOrderBy() {
        selectModel.setOrderBy(selectModel.getOrderBy());
    }

    @Override
    protected void CompleteJoin() {
        List<JoinClauseModel> joinClauseModels = selectModel.getJoins();
        joinClauseModels.add(lastJoinClause);
        selectModel.setJoins(joinClauseModels);
        lastJoinClause = null;
    }

    @Override
    public void Execute() {
        CompleteOrderBy();
        if (whereRootClause != null)
            CompleteWhere();
        if (lastJoinClause != null)
            CompleteJoin();
    }

}
