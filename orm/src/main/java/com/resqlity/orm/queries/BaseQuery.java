package com.resqlity.orm.queries;

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

public abstract class BaseQuery {
    private String tableName;
    private String tableSchema;
    private Class<?> baseTableClass;
    private List<EntityTableMapping> entityTableMappingList;
    private List<EntityPropertyMapping> entityPropertyMappings;

    public BaseQuery(Class<?> tableClass) {
        this.baseTableClass = tableClass;
        entityPropertyMappings = new ArrayList<>();
        entityTableMappingList = new ArrayList<>();
    }

    protected WhereClauseModel whereRootClause;
    protected JoinClauseModel lastJoinClause;


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

    public abstract void Execute() throws Exception;

    protected ResqlityProperty getProperty(Class<?> tableClass, String fieldName) throws NoSuchFieldException {
        return tableClass.getDeclaredField(fieldName).getAnnotation(ResqlityProperty.class);
    }

    protected ResqlityProperty getProperty(String fieldName) throws NoSuchFieldException {
        return baseTableClass.getDeclaredField(fieldName).getAnnotation(ResqlityProperty.class);
    }

    protected ResqlityTable getTable() {
        return baseTableClass.getAnnotation(ResqlityTable.class);
    }

    protected ResqlityTable getTable(Class<?> tableClass) {
        return tableClass.getAnnotation(ResqlityTable.class);
    }

    protected String getTableName(Class<?> tableClass) {
        return getTable(tableClass).TableName();
    }

    protected String getTableName() {
        return getTable(baseTableClass).TableName();
    }

    protected String getTableSchema(Class<?> tableClass) {
        return getTable(tableClass).TableSchema();
    }

    protected String getTableSchema() {
        return getTable(baseTableClass).TableSchema();
    }

    protected String getPropertyName(String fieldName) throws NoSuchFieldException {
        return getProperty(fieldName).ColumnName();
    }

    protected String getPropertyName(Class<?> baseTableClass, String fieldName) throws NoSuchFieldException {
        return getProperty(baseTableClass, fieldName).ColumnName();
    }

    protected Class<?> getBaseTableClass() {
        return baseTableClass;
    }
}
