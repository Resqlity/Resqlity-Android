package com.resqlity.orm.queries;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.JoinType;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.functions.join.JoinFunction;
import com.resqlity.orm.models.clausemodels.JoinClauseModel;

import java.util.List;

public class SelectJoinFunction extends JoinFunction {

    SelectQuery query;

    public SelectJoinFunction(SelectQuery baseQuery, JoinClauseModel joinClauseModel, Class<?> parentClass) {
        super(baseQuery, joinClauseModel, parentClass);
        query = baseQuery;
    }

    /**
     * @@param joinClass       Class to join
     * @@param fieldName       Field to compare
     * @@param parentFieldName Parent field to compare with child
     * @@param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @@return
     * @@throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction ChildInnerJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        Join(joinClass, fieldName, parentFieldName, comparator, JoinType.INNER);
        return this;
    }

    /**
     * @@param joinClass       Class to join
     * @@param fieldName       Field to compare
     * @@param parentFieldName Parent field to compare with child
     * @@param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @@return
     * @@throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction ChildLeftJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        Join(joinClass, fieldName, parentFieldName, comparator, JoinType.LEFT);
        return this;
    }

    /**
     * @@param joinClass       Class to join
     * @@param fieldName       Field to compare
     * @@param parentFieldName Parent field to compare with child
     * @@param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @@return
     * @@throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction ChildRightJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        Join(joinClass, fieldName, parentFieldName, comparator, JoinType.RIGHT);
        return this;
    }

    /**
     * @@param joinClass       Class to join
     * @@param fieldName       Field to compare
     * @@param parentFieldName Parent field to compare with child
     * @@param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @@return
     * @@throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction ChildLeftOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        Join(joinClass, fieldName, parentFieldName, comparator, JoinType.LEFT_OUTER);
        return this;
    }

    /**
     * @@param joinClass       Class to join
     * @@param fieldName       Field to compare
     * @@param parentFieldName Parent field to compare with child
     * @@param comparator      Comparator such as Comparator.Equal,Comparator.NotEqual
     * @@return
     * @@throws ResqlityDbException
     */
    @Override
    public SelectJoinFunction ChildRightOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws ResqlityDbException {
        Join(joinClass, fieldName, parentFieldName, comparator, JoinType.RIGHT_OUTER);
        return this;
    }

    private void Join(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator, JoinType type) throws ResqlityDbException {
        JoinClauseModel head = joinClauseModel;
        List<JoinClauseModel> joins = head.getJoins();
        joins.add(getJoinClauseModel(query.getTableName(joinClass),
                query.getTableSchema(joinClass),
                query.getPropertyName(joinClass, fieldName),
                query.getPropertyName(parentClass, parentFieldName),
                comparator,
                type));
        head.setJoins(joins);
    }

    @Override
    public SelectQuery Query() {
        query.CompleteJoin();
        return query;
    }
}
