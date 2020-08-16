package com.resqlity.orm.functions.join;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.JoinType;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.models.clausemodels.JoinClauseModel;
import com.resqlity.orm.queries.BaseFilterableQuery;

public abstract class JoinFunction {
    protected BaseFilterableQuery baseQuery;
    protected JoinClauseModel joinClauseModel;
    protected Class<?> baseClass;

    public JoinFunction(BaseFilterableQuery baseQuery, JoinClauseModel joinClauseModel, Class<?> parentClass) {
        this.baseQuery = baseQuery;
        this.joinClauseModel = joinClauseModel;
        this.baseClass = parentClass;
    }

    public abstract JoinFunction InnerJoin(Class<?> joinClass,
                                           String fieldName,
                                           String parentFieldName,
                                           Comparator comparator) throws ResqlityDbException;

    public abstract JoinFunction LeftJoin(Class<?> joinClass,
                                          String fieldName,
                                          String parentFieldName,
                                          Comparator comparator) throws ResqlityDbException;

    public abstract JoinFunction RightJoin(Class<?> joinClass,
                                           String fieldName,
                                           String parentFieldName,
                                           Comparator comparator) throws ResqlityDbException;

    public abstract JoinFunction LeftOuterJoin(Class<?> joinClass,
                                               String fieldName,
                                               String parentFieldName,
                                               Comparator comparator) throws ResqlityDbException;

    public abstract JoinFunction RightOuterJoin(Class<?> joinClass,
                                                String fieldName,
                                                String parentFieldName,
                                                Comparator comparator) throws ResqlityDbException;

    public abstract BaseFilterableQuery Query();

    protected JoinClauseModel getJoinClauseModel(String tableName,
                                                 String tableSchema,
                                                 String columnName,
                                                 String parentColumnName,
                                                 Comparator comparator,
                                                 JoinType joinType) {
        return new JoinClauseModel(tableName, tableSchema, columnName, parentColumnName, comparator, joinType);
    }
}
