package com.resqlity.orm.queries;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.examples.SampleClass;
import com.resqlity.orm.functions.join.JoinFunction;
import com.resqlity.orm.functions.where.WhereFunction;
import com.resqlity.orm.helpers.JsonHelper;
import com.resqlity.orm.models.responses.ResqlityResponse;

import java.util.ArrayList;
import java.util.List;

public class InsertQuery extends BaseInsertQuery {
    private ArrayList<Object> Data;


    public InsertQuery(Class<?> tableClass) {
        super(tableClass);
        Data = new ArrayList<Object>();
    }

    public InsertQuery Insert(Object data) {
        Data.add(data);
        return this;
    }


    @Override
    public void Execute() throws Exception {
        String requestString=JsonHelper.Serialize(Data);
    }
}
