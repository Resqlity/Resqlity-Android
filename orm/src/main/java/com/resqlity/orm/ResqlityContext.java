package com.resqlity.orm;

import com.resqlity.orm.queries.DeleteQuery;
import com.resqlity.orm.queries.InsertQuery;
import com.resqlity.orm.queries.SelectQuery;
import com.resqlity.orm.queries.UpdateQuery;

public class ResqlityContext {
    String apiKey;

    public ResqlityContext(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public SelectQuery Select(Class<? extends Object> obj) {
        return new SelectQuery(obj, this);
    }

    public UpdateQuery Update(Class<? extends Object> obj) {
        return new UpdateQuery(obj, this);
    }

    public DeleteQuery Delete(Class<? extends Object> obj) {
        return new DeleteQuery(obj, this);
    }

    public InsertQuery Insert(Class<? extends Object> obj) {
        return new InsertQuery(obj, this);
    }


}
