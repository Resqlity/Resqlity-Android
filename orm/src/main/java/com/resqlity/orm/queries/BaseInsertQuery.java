package com.resqlity.orm.queries;

import com.resqlity.orm.ResqlityContext;

public abstract class BaseInsertQuery extends BaseQuery {

    public BaseInsertQuery(Class<?> tableClass, ResqlityContext dbContext) {
        super(tableClass,dbContext);
    }
}
