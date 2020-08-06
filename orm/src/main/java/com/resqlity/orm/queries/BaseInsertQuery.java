package com.resqlity.orm.queries;

public abstract class BaseInsertQuery extends BaseQuery {

    public BaseInsertQuery(Class<?> tableClass) {
        super(tableClass);
    }
}
