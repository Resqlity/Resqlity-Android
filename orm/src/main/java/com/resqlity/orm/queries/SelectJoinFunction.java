package com.resqlity.orm.queries;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.JoinType;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.functions.join.JoinFunction;
import com.resqlity.orm.models.clausemodels.JoinClauseModel;
import com.resqlity.orm.queryobjects.select.SelectColumn;

import java.util.ArrayList;
import java.util.List;

public class SelectJoinFunction extends JoinFunction {

    SelectQuery query;
    List<SelectColumn> selectColumns;
    SelectJoinFunction parent;

    public SelectJoinFunction(SelectQuery baseQuery, JoinClauseModel joinClauseModel, Class<?> baseClass, SelectJoinFunction parent) {
        super(baseQuery, joinClauseModel, baseClass);
        query = baseQuery;
        this.parent = parent;
        selectColumns = new ArrayList<>();
    }

    public SelectJoinFunction Select(String field) throws ResqlityDbException {
        List<SelectColumn> columns = joinClauseModel.getColumns();
        SelectColumn column = new SelectColumn(query.getTableName(baseClass), query.getTableSchema(baseClass), query.getPropertyName(baseClass, field), field);
        columns.add(column);
        joinClauseModel.setColumns(columns);
        return this;
    }

    /**
     * @param joinClass       Class to join
     * @param fieldName       Field to compare
     * @param parentFieldName Parent field to compare with child
     * @param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @return
     * @throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction InnerJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.INNER);
    }

    /**
     * @param joinClass       Class to join
     * @param fieldName       Field to compare
     * @param parentFieldName Parent field to compare with child
     * @param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @return
     * @throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction LeftJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.LEFT);
    }

    /**
     * @param joinClass       Class to join
     * @param fieldName       Field to compare
     * @param parentFieldName Parent field to compare with child
     * @param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @return
     * @throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction RightJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.RIGHT);
    }

    /**
     * @param joinClass       Class to join
     * @param fieldName       Field to compare
     * @param parentFieldName Parent field to compare with child
     * @param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @return
     * @throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction LeftOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.LEFT_OUTER);
    }

    /**
     * @param joinClass       Class to join
     * @param fieldName       Field to compare
     * @param parentFieldName Parent field to compare with child
     * @param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @return
     * @throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction RightOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.RIGHT_OUTER);
    }

    private SelectJoinFunction Join(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator, JoinType type) throws ResqlityDbException {
//        JoinClauseModel head = joinClauseModel;
//        List<JoinClauseModel> joins = head.getJoins();
        JoinClauseModel joinClauseModel = getJoinClauseModel(query.getTableName(joinClass),
                query.getTableSchema(joinClass),
                query.getPropertyName(joinClass, fieldName),
                query.getPropertyName(baseClass, parentFieldName),
                comparator,
                type);
//        joins.add(joinClauseModel);
//        head.setJoins(joins);
        return new SelectJoinFunction(query, joinClauseModel, joinClass, this);
    }

    protected void CompleteChildFunction(JoinClauseModel model) {
        List<JoinClauseModel> joinClauseModels = joinClauseModel.getJoins();
        joinClauseModels.add(model);
        joinClauseModel.setJoins(joinClauseModels);
    }

    public SelectJoinFunction Parent() throws ResqlityDbException {
        if (parent == null)
            throw new ResqlityDbException("Parent NULL");
        CompleteChildFunction(joinClauseModel);
        return parent;
    }

    @Override
    public SelectQuery Query() {
        SelectJoinFunction iterator = this;
        while (iterator.parent != null) {
            iterator.parent.CompleteChildFunction(iterator.joinClauseModel);
            iterator = iterator.parent;
        }
        query.CompleteJoin();
        return query;
    }
}
