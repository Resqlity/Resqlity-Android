package com.resqlity.orm.models.querymodels;

import com.resqlity.orm.models.clausemodels.WhereClauseModel;
import com.resqlity.orm.models.extensions.IHasTransaction;

import java.util.ArrayList;
import java.util.List;

public class DeleteModel extends BaseModel implements IHasTransaction {

    private List<WhereClauseModel> wheres;
    private boolean useTransaction;

    public DeleteModel(String apiKey, String tableName, String tableSchema) {
        super(apiKey, tableName, tableSchema);
        wheres = new ArrayList<>();
        useTransaction = false;
    }

    public List<WhereClauseModel> getWheres() {
        return wheres;
    }

    public void setWheres(List<WhereClauseModel> wheres) {
        this.wheres = wheres;
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
