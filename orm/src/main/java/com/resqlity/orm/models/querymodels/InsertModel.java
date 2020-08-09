package com.resqlity.orm.models.querymodels;

import java.util.List;

public class InsertModel extends BaseModel {
    public InsertModel(String apiKey, String tableName, String tableSchema, boolean useTransaction, List<Object> datas) {
        super(apiKey, tableName, tableSchema);
        this.useTransaction = useTransaction;
        this.datas = datas;
    }
    private boolean useTransaction;
    private List<Object> datas;

}
