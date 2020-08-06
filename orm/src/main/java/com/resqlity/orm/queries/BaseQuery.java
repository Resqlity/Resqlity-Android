package com.resqlity.orm.queries;

import com.resqlity.orm.annotations.ResqlityProperty;
import com.resqlity.orm.annotations.ResqlityTable;
import com.resqlity.orm.functions.orderBy.OrderByFunction;
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


    public abstract WhereFunction Where(String fieldName, Object compareTo, Comparator comparator) throws NoSuchFieldException;

    public abstract OrderByFunction OrderBy(Class<?> tableClass, String field, boolean isAsc) throws NoSuchFieldException;

    public abstract OrderByFunction OrderBy(String field, boolean isAsc) throws NoSuchFieldException;

    protected abstract void CompleteWhere();

    protected abstract void CompleteOrderBy();

    public abstract void Execute();

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
