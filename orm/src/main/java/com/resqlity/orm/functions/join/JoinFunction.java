package com.resqlity.orm.functions.join;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.JoinType;
import com.resqlity.orm.models.clausemodels.JoinClauseModel;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.queries.BaseQuery;

public abstract class JoinFunction {
    protected BaseQuery baseQuery;
    protected JoinClauseModel joinClauseModel;
    protected Class<?> parentClass;
    public JoinFunction(BaseQuery baseQuery, JoinClauseModel joinClauseModel,Class<?> parentClass) {
        this.baseQuery = baseQuery;
        this.joinClauseModel = joinClauseModel;
        this.parentClass=parentClass;
    }

    public abstract JoinFunction ChildInnerJoin(Class<?> joinClass,
                                           String fieldName,
                                           String parentFieldName,
                                           Comparator comparator) throws NoSuchFieldException;

    public abstract JoinFunction ChildLeftJoin(Class<?> joinClass,
                                          String fieldName,
                                          String parentFieldName,
                                          Comparator comparator) throws NoSuchFieldException;

    public abstract JoinFunction ChildRightJoin(Class<?> joinClass,
                                           String fieldName,
                                           String parentFieldName,
                                           Comparator comparator) throws NoSuchFieldException;

    public abstract JoinFunction ChildLeftOuterJoin(Class<?> joinClass,
                                               String fieldName,
                                               String parentFieldName,
                                               Comparator comparator) throws NoSuchFieldException;

    public abstract JoinFunction ChildRightOuterJoin(Class<?> joinClass,
                                                String fieldName,
                                                String parentFieldName,
                                                Comparator comparator) throws NoSuchFieldException;

    public abstract BaseQuery Query();

    public void Execute() throws Exception {
        baseQuery.Execute();
    }

    protected JoinClauseModel getJoinClauseModel(String tableName,
                                                 String tableSchema,
                                                 String columnName,
                                                 String parentColumnName,
                                                 Comparator comparator,
                                                 JoinType joinType) {
        return new JoinClauseModel(tableName, tableSchema, columnName, parentColumnName, comparator, joinType);
    }
}
