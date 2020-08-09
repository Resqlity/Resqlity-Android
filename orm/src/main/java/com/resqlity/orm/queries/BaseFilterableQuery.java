package com.resqlity.orm.queries;

import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.annotations.ResqlityProperty;
import com.resqlity.orm.annotations.ResqlityTable;
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

public abstract class BaseFilterableQuery extends BaseQuery {


    protected WhereClauseModel whereRootClause;
    protected JoinClauseModel lastJoinClause;

    public BaseFilterableQuery(Class<?> tableClass, ResqlityContext dbContext) {
        super(tableClass, dbContext);
    }


    public abstract WhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException;

    public abstract JoinFunction InnerJoin(Class<?> joinClass,
                                           String fieldName,
                                           String parentFieldName,
                                           Comparator comparator) throws NoSuchFieldException, NoSuchMethodException;

    public abstract JoinFunction LeftJoin(Class<?> joinClass,
                                          String fieldName,
                                          String parentFieldName,
                                          Comparator comparator) throws NoSuchFieldException, NoSuchMethodException;

    public abstract JoinFunction RightJoin(Class<?> joinClass,
                                           String fieldName,
                                           String parentFieldName,
                                           Comparator comparator) throws NoSuchFieldException, NoSuchMethodException;

    public abstract JoinFunction LeftOuterJoin(Class<?> joinClass,
                                               String fieldName,
                                               String parentFieldName,
                                               Comparator comparator) throws NoSuchFieldException, NoSuchMethodException;

    public abstract JoinFunction RightOuterJoin(Class<?> joinClass,
                                                String fieldName,
                                                String parentFieldName,
                                                Comparator comparator) throws NoSuchFieldException, NoSuchMethodException;

    protected abstract void CompleteWhere();

    protected abstract void CompleteJoin() throws NoSuchMethodException;
}
