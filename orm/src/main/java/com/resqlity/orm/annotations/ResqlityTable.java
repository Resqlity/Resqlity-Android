package com.resqlity.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ResqlityTable
 * TableName : Presents Table Name In Database
 * TableSchema : Present Table Schema In Database
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ResqlityTable {
    public String TableName();

    public String TableSchema();
}
