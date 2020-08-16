package com.resqlity.orm.queries;

import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.annotations.ResqlityProperty;
import com.resqlity.orm.annotations.ResqlityTable;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.models.mappingmodels.EntityPropertyMapping;
import com.resqlity.orm.models.mappingmodels.EntityTableMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Query
 */
public abstract class BaseQuery {
    /**
     * Table Name
     */
    protected String tableName;
    /**
     * Table Schema
     */
    protected String tableSchema;
    /**
     * Query Base Table
     */
    protected Class<?> baseTableClass;
    /**
     * Resqlity Context
     */
    protected ResqlityContext dbContext;

    public BaseQuery(Class<?> tableClass, ResqlityContext dbContext) {
        this.baseTableClass = tableClass;
        this.dbContext = dbContext;
    }


    /**
     * @param tableClass Table Class
     * @param fieldName  Field Name
     * @return ResqlityProperty
     * @throws ResqlityDbException
     */
    protected ResqlityProperty getProperty(Class<?> tableClass, String fieldName) throws ResqlityDbException {
        try {
            return tableClass.getDeclaredField(fieldName).getAnnotation(ResqlityProperty.class);
        } catch (NoSuchFieldException e) {
            throw new ResqlityDbException(e.getMessage(), e);
        }
    }

    /**
     * @param fieldName Field Name
     * @return ResqlityProperty
     * @throws ResqlityDbException
     */
    protected ResqlityProperty getProperty(String fieldName) throws ResqlityDbException {
        try {
            return baseTableClass.getDeclaredField(fieldName).getAnnotation(ResqlityProperty.class);
        } catch (NoSuchFieldException e) {
            throw new ResqlityDbException(e.getMessage(), e);
        }
    }

    /**
     * @return ResqlityTable Of Base Query Class
     */
    protected ResqlityTable getTable() {
        return baseTableClass.getAnnotation(ResqlityTable.class);
    }

    /**
     * @param tableClass Table Class
     * @return ResqlityTable
     */
    protected ResqlityTable getTable(Class<?> tableClass) {
        return tableClass.getAnnotation(ResqlityTable.class);
    }

    /**
     * @param tableClass Table Class
     * @return Table Name
     */
    protected String getTableName(Class<?> tableClass) {
        return getTable(tableClass).TableName();
    }

    /**
     * @return Table Name Of Base Query Class
     */
    protected String getTableName() {
        return getTable(baseTableClass).TableName();
    }

    /**
     * @param tableClass Table Class
     * @return Table Schema Of tableClass
     */
    protected String getTableSchema(Class<?> tableClass) {
        return getTable(tableClass).TableSchema();
    }

    /**
     * @return Table Schema Of Base Query Class
     */
    protected String getTableSchema() {
        return getTable(baseTableClass).TableSchema();
    }

    /**
     * @param fieldName Field Name
     * @return Property Name of fieldName ın Base Query Class
     * @throws ResqlityDbException
     */
    protected String getPropertyName(String fieldName) throws ResqlityDbException {
        return getProperty(fieldName).ColumnName();
    }

    /**
     * @param baseTableClass Table Class
     * @param fieldName      Field Name
     * @return Property Name Of Field Name That In BaseTableClass
     * @throws ResqlityDbException
     */
    protected String getPropertyName(Class<?> baseTableClass, String fieldName) throws ResqlityDbException {
        return getProperty(baseTableClass, fieldName).ColumnName();
    }

    /**
     * @return Base Table Class
     */
    protected Class<?> getBaseTableClass() {
        return baseTableClass;
    }
}
