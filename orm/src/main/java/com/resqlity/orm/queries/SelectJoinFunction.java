package com.resqlity.orm.queries;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.JoinType;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.functions.join.JoinFunction;
import com.resqlity.orm.models.clausemodels.JoinClauseModel;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.queryobjects.select.SelectColumn;

import java.util.ArrayList;
import java.util.List;

public class SelectJoinFunction extends JoinFunction {

    SelectQuery query;
    List<SelectColumn> selectColumns;
    SelectJoinFunction parent;


    /**
     * @param baseQuery       SelectQuery
     * @param joinClauseModel Linked Join Object
     * @param baseClass       Join Class
     * @param parent          Parent Join Function
     */
    public SelectJoinFunction(SelectQuery baseQuery, JoinClauseModel joinClauseModel, Class<?> baseClass, SelectJoinFunction parent) {
        super(baseQuery, joinClauseModel, baseClass);
        query = baseQuery;
        this.parent = parent;
        selectColumns = new ArrayList<>();
    }

    /**
     * @param field Field To Select
     * @return SelectJoinFunction
     * @throws ResqlityDbException
     */
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
     * @return SelectJoinFunction
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
     * @return SelectJoinFunction
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
     * @return SelectJoinFunction
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
     * @return SelectJoinFunction
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
     * @return SelectJoinFunction
     * @throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction RightOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        return Join(joinClass, fieldName, parentFieldName, comparator, JoinType.RIGHT_OUTER);
    }

    /**
     * @param fieldName  Field Name
     * @param compareTo  Compare To Value
     * @param comparator Object Comparator
     * @return SelectJoinWhereFunction
     * @throws ResqlityDbException
     */
    @Override
    public SelectJoinWhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException {
        WhereClauseModel root = new WhereClauseModel(query.getTableName(baseClass), query.getTableSchema(baseClass), query.getPropertyName(baseClass, fieldName), compareTo, comparator);
        whereRootClause = root;
        return new SelectJoinWhereFunction(root, query, this);
    }

    /**
     * @param joinClass       Join Class
     * @param fieldName       Field Name
     * @param parentFieldName Parent Field Name
     * @param comparator      Object Comparator
     * @param type            Join Type
     * @return SelectJoinFunction
     * @throws ResqlityDbException
     */
    private SelectJoinFunction Join(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator, JoinType type) throws ResqlityDbException {
        JoinClauseModel joinClauseModel = getJoinClauseModel(query.getTableName(joinClass),
                query.getTableSchema(joinClass),
                query.getPropertyName(joinClass, fieldName),
                query.getPropertyName(baseClass, parentFieldName),
                comparator,
                type);
        return new SelectJoinFunction(query, joinClauseModel, joinClass, this);
    }

    /**
     * @param model Child Join Function
     */
    protected void CompleteChildFunction(SelectJoinFunction model) {
        if (model.whereRootClause != null)
            model.CompleteWhere();
        List<JoinClauseModel> joinClauseModels = joinClauseModel.getJoins();
        joinClauseModels.add(model.joinClauseModel);
        joinClauseModel.setJoins(joinClauseModels);
    }

    /**
     * @return Parent Function
     * @throws ResqlityDbException
     */
    public SelectJoinFunction Parent() throws ResqlityDbException {
        if (parent == null)
            throw new ResqlityDbException("Parent NULL");
        CompleteChildFunction(this);
        return parent;
    }

    /**
     * @return SelectQuery
     */
    @Override
    public SelectQuery Query() {
        SelectJoinFunction iterator = this;
        while (iterator.parent != null) {
            iterator.parent.CompleteChildFunction(iterator);
            iterator = iterator.parent;
        }
        if (whereRootClause != null)
            CompleteWhere();
        query.CompleteJoin();
        return query;
    }

    /**
     * Completes Linked Where Object
     */
    @Override
    protected void CompleteWhere() {
        List<WhereClauseModel> whereClauseModels = joinClauseModel.getWheres();
        whereClauseModels.add(whereRootClause);
        joinClauseModel.setWheres(whereClauseModels);
        whereRootClause = null;
    }
}
