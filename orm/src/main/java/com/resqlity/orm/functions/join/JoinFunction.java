package com.resqlity.orm.functions.join;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.JoinType;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.functions.where.WhereFunction;
import com.resqlity.orm.models.clausemodels.JoinClauseModel;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.queries.BaseFilterableQuery;

/**
 * Join Function
 */
public abstract class JoinFunction {
    /**
     * Base Query
     */
    protected BaseFilterableQuery baseQuery;
    /**
     * Join Clause
     */
    protected JoinClauseModel joinClauseModel;
    /**
     * Join Class
     */
    protected Class<?> baseClass;
    /**
     * Join Where Linked Objects
     */
    protected WhereClauseModel whereRootClause;

    public JoinFunction(BaseFilterableQuery baseQuery, JoinClauseModel joinClauseModel, Class<?> parentClass) {
        this.baseQuery = baseQuery;
        this.joinClauseModel = joinClauseModel;
        this.baseClass = parentClass;
    }

    /**
     * @param joinClass       Join Class
     * @param fieldName       Field Name
     * @param parentFieldName Parent Class Field Name
     * @param comparator      Object Comparator
     * @return JoinFunction
     * @throws ResqlityDbException
     */
    public abstract JoinFunction InnerJoin(Class<?> joinClass,
                                           String fieldName,
                                           String parentFieldName,
                                           Comparator comparator) throws ResqlityDbException;

    /**
     * @param joinClass       Join Class
     * @param fieldName       Field Name
     * @param parentFieldName Parent Class Field Name
     * @param comparator      Object Comparator
     * @return JoinFunction
     * @throws ResqlityDbException
     */
    public abstract JoinFunction LeftJoin(Class<?> joinClass,
                                          String fieldName,
                                          String parentFieldName,
                                          Comparator comparator) throws ResqlityDbException;

    /**
     * @param joinClass       Join Class
     * @param fieldName       Field Name
     * @param parentFieldName Parent Class Field Name
     * @param comparator      Object Comparator
     * @return JoinFunction
     * @throws ResqlityDbException
     */
    public abstract JoinFunction RightJoin(Class<?> joinClass,
                                           String fieldName,
                                           String parentFieldName,
                                           Comparator comparator) throws ResqlityDbException;

    /**
     * @param joinClass       Join Class
     * @param fieldName       Field Name
     * @param parentFieldName Parent Class Field Name
     * @param comparator      Object Comparator
     * @return JoinFunction
     * @throws ResqlityDbException
     */
    public abstract JoinFunction LeftOuterJoin(Class<?> joinClass,
                                               String fieldName,
                                               String parentFieldName,
                                               Comparator comparator) throws ResqlityDbException;

    /**
     * @param joinClass       Join Class
     * @param fieldName       Field Name
     * @param parentFieldName Parent Class Field Name
     * @param comparator      Object Comparator
     * @return JoinFunction
     * @throws ResqlityDbException
     */
    public abstract JoinFunction RightOuterJoin(Class<?> joinClass,
                                                String fieldName,
                                                String parentFieldName,
                                                Comparator comparator) throws ResqlityDbException;

    /**
     * @param fieldName  Field Name
     * @param compareTo  Compare To Value
     * @param comparator Object Comparator
     * @return WhereFunction
     * @throws ResqlityDbException
     */
    public abstract WhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException;

    /**
     * @return BaseFilterableQuery
     */
    public abstract BaseFilterableQuery Query();

    /**
     * Completes Linked Where Object
     */
    protected abstract void CompleteWhere();

    /**
     * @return Join Class
     */
    public Class<?> getBaseClass() {
        return baseClass;
    }

    /**
     * @param tableName        Table Name
     * @param tableSchema      Table Schema
     * @param columnName       Column Name
     * @param parentColumnName Parent Column Name
     * @param comparator       Object Comparator
     * @param joinType         Join Type
     * @return New JoinClauseModel Instance
     */
    protected JoinClauseModel getJoinClauseModel(String tableName,
                                                 String tableSchema,
                                                 String columnName,
                                                 String parentColumnName,
                                                 Comparator comparator,
                                                 JoinType joinType) {
        return new JoinClauseModel(tableName, tableSchema, columnName, parentColumnName, comparator, joinType);
    }
}
