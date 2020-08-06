package com.resqlity.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method , Field or Constructor Not Yet Implemented
 */
@Target({
        ElementType.ANNOTATION_TYPE,
        ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotImplemented {
    boolean value() default true;
}
