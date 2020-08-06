package com.resqlity.orm.models.querymodels;

import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.models.extensions.IHasTransaction;
import com.resqlity.orm.queryobjects.update.UpdateQueryObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateModel implements IHasTransaction {
    private List<WhereClauseModel> wheres;
    private List<UpdateQueryObject> model;
    private boolean useTransaction;

    public UpdateModel() {
        useTransaction = false;
        wheres=new ArrayList<>();
        model=new ArrayList<>();
    }

    public List<WhereClauseModel> getWheres() {
        return wheres;
    }

    public void setWheres(List<WhereClauseModel> wheres) {
        this.wheres = wheres;
    }

    public List<UpdateQueryObject> getModel() {
        return model;
    }

    public void setModel(List<UpdateQueryObject> model) {
        this.model = model;
    }

    @Override
    public boolean isUseTransaction() {
        return useTransaction;
    }

    @Override
    public void setUseTransaction(boolean useTransaction) {
        this.useTransaction = useTransaction;
    }
}
