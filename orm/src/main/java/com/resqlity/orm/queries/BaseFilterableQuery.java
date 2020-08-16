package com.resqlity.orm.queries;

import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.annotations.ResqlityProperty;
import com.resqlity.orm.annotations.ResqlityTable;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.functions.join.JoinFunction;
import com.resqlity.orm.functions.orderBy.OrderByFunction;
import com.resqlity.orm.models.clausemodels.JoinClauseModel;
import com.resqlity.orm.models.clausemodels.OrderByClauseModel;
import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.functions.where.WhereFunction;
import com.resqlity.orm.models.mappingmodels.EntityPropertyMapping;
import com.resqlity.orm.models.mappingmodels.EntityTableMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Filterable Query
 */
public abstract class BaseFilterableQuery extends BaseQuery {


    /**
     * Linked Where Clause Object
     */
    protected WhereClauseModel whereRootClause;
    /**
     * Linked Join Clause Object
     */
    protected JoinClauseModel lastJoinClause;

    /**
     * @param tableClass Table Class
     * @param dbContext  Resqlity Context
     */
    public BaseFilterableQuery(Class<?> tableClass, ResqlityContext dbContext) {
        super(tableClass, dbContext);
    }


    /**
     * @param fieldName  Field Name
     * @param compareTo  Compare To Value
     * @param comparator Object Comparator
     * @return WhereFunction
     * @throws ResqlityDbException
     */
    public abstract WhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws ResqlityDbException;

    /**
     * @param joinClass       Join With Class
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
     * @param joinClass       Join With Class
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
     * @param joinClass       Join With Class
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
     * @param joinClass       Join With Class
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
     * @param joinClass       Join With Class
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
     * Completes Linked Where Clause Object
     */
    protected abstract void CompleteWhere();

    /**
     * Completes Linked Join Object
     *
     * @throws ResqlityDbException
     */
    protected abstract void CompleteJoin() throws ResqlityDbException;
}
