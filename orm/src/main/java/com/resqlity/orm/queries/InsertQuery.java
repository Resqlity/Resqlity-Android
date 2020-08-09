package com.resqlity.orm.queries;

import com.google.gson.JsonObject;
import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.consts.Endpoints;
import com.resqlity.orm.helpers.JsonHelper;
import com.resqlity.orm.models.querymodels.InsertModel;
import com.resqlity.orm.tasks.InsertRequestAsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.HttpsURLConnection;

public class InsertQuery extends BaseInsertQuery {
    private ArrayList<Object> Data;
    private Map ttp = new HashMap();


    public InsertQuery(Class<?> tableClass, ResqlityContext context) {
        super(tableClass, context);
        Data = new ArrayList<Object>();
    }

    public InsertQuery Insert(Object data) throws Exception {

        HashMap<String, Object> values = new HashMap<>();

        Object o = new Object();
        for (Field declaredField :
                data.getClass().getDeclaredFields()) {
            String propertyName = getPropertyName(declaredField.getName());
            declaredField.setAccessible(true);
            Object value = declaredField.get(data);
            values.put(propertyName, value);
        }
        Data.add(values);
        return this;
    }


    @Override
    public void Execute() throws Exception {
        String urlString = Endpoints.INSERT_URL; // URL to call
        InsertModel insertModel = new InsertModel(dbContext.getApiKey(),
                getTableName(),
                getTableSchema(),
                true,
                Data);
        String data = JsonHelper.Serialize(insertModel); //data to post
        InsertRequestAsyncTask task = new InsertRequestAsyncTask(data, dbContext.getApiKey(), getTableName(), getTableSchema());
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(task::run);

    }

}

