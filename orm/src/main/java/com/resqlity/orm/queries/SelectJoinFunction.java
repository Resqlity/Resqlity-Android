package com.resqlity.orm.queries;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.JoinType;
import com.resqlity.orm.functions.join.JoinFunction;
import com.resqlity.orm.models.clausemodels.JoinClauseModel;

import java.util.List;

public class SelectJoinFunction extends JoinFunction {

    SelectQuery query;

    public SelectJoinFunction(SelectQuery baseQuery, JoinClauseModel joinClauseModel, Class<?> parentClass) {
        super(baseQuery, joinClauseModel, parentClass);
        query = baseQuery;
    }

    @Override
    public SelectJoinFunction ChildInnerJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException {
        Join(joinClass, fieldName, parentFieldName, comparator, JoinType.INNER);
        return this;
    }

    @Override
    public SelectJoinFunction ChildLeftJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException {
        Join(joinClass, fieldName, parentFieldName, comparator, JoinType.LEFT);
        return this;
    }

    @Override
    public SelectJoinFunction ChildRightJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException {
        Join(joinClass, fieldName, parentFieldName, comparator, JoinType.RIGHT);
        return this;
    }

    @Override
    public SelectJoinFunction ChildLeftOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException {
        Join(joinClass, fieldName, parentFieldName, comparator, JoinType.LEFT_OUTER);
        return this;
    }

    @Override
    public SelectJoinFunction ChildRightOuterJoin(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator) throws NoSuchFieldException {
        Join(joinClass, fieldName, parentFieldName, comparator, JoinType.RIGHT_OUTER);
        return this;
    }

    private void Join(Class<?> joinClass, String fieldName, String parentFieldName, Comparator comparator, JoinType type) throws NoSuchFieldException {
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
