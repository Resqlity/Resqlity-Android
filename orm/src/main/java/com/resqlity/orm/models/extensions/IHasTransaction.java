package com.resqlity.orm.models.extensions;

public interface IHasTransaction {
    boolean isUseTransaction();

    void setUseTransaction(boolean useTransaction);
}
