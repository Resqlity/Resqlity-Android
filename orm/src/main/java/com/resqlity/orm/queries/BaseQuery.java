package com.resqlity.orm.queries;

import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.annotations.ResqlityProperty;
import com.resqlity.orm.annotations.ResqlityTable;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.models.mappingmodels.EntityPropertyMapping;
import com.resqlity.orm.models.mappingmodels.EntityTableMapping;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseQuery {
    protected String tableName;
    protected String tableSchema;
    protected Class<?> baseTableClass;
    protected List<EntityTableMapping> entityTableMappingList;
    protected List<EntityPropertyMapping> entityPropertyMappings;
    protected ResqlityContext dbContext;

    public BaseQuery(Class<?> tableClass, ResqlityContext dbContext) {
        this.baseTableClass = tableClass;
        entityPropertyMappings = new ArrayList<>();
        entityTableMappingList = new ArrayList<>();
        this.dbContext = dbContext;
    }

//    public abstract void Execute() throws Exception;

    protected ResqlityProperty getProperty(Class<?> tableClass, String fieldName) throws ResqlityDbException {
        try {
            return tableClass.getDeclaredField(fieldName).getAnnotation(ResqlityProperty.class);
        } catch (NoSuchFieldException e) {
            throw new ResqlityDbException(e.getMessage(),e);
        }
    }

    protected ResqlityProperty getProperty(String fieldName) throws ResqlityDbException {
        try {
            return baseTableClass.getDeclaredField(fieldName).getAnnotation(ResqlityProperty.class);
        } catch (NoSuchFieldException e) {
            throw new ResqlityDbException(e.getMessage(),e);
        }
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

    protected String getPropertyName(String fieldName) throws ResqlityDbException {
        return getProperty(fieldName).ColumnName();
    }

    protected String getPropertyName(Class<?> baseTableClass, String fieldName) throws ResqlityDbException {
        return getProperty(baseTableClass, fieldName).ColumnName();
    }

    protected Class<?> getBaseTableClass() {
        return baseTableClass;
    }
}
