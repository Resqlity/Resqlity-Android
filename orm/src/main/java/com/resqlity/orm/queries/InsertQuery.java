package com.resqlity.orm.queries;

import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.consts.Endpoints;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.helpers.JsonHelper;
import com.resqlity.orm.helpers.ResqlityHelpers;
import com.resqlity.orm.models.querymodels.InsertModel;
import com.resqlity.orm.models.responses.ResqlitySimpleResponse;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class InsertQuery extends BaseInsertQuery {
    private ArrayList<Object> Data;
    private Map ttp = new HashMap();


    public InsertQuery(Class<?> tableClass, ResqlityContext context) {
        super(tableClass, context);
        Data = new ArrayList<Object>();
    }

    public InsertQuery Insert(Object... data) throws ResqlityDbException {
        for (Object t :
                data) {
            Insert(t);
        }
        return this;
    }

    public InsertQuery Insert(List<Object> data) throws ResqlityDbException {
        for (Object t :
                data) {
            Insert(t);
        }
        return this;
    }

    public InsertQuery Insert(Object data) throws ResqlityDbException {

        HashMap<String, Object> values = new HashMap<>();

        Object o = new Object();
        for (Field declaredField :
                data.getClass().getDeclaredFields()) {
            String propertyName = getPropertyName(declaredField.getName());
            declaredField.setAccessible(true);
            Object value = null;
            try {
                value = declaredField.get(data);
            } catch (IllegalAccessException e) {
                throw new ResqlityDbException(e.getMessage(), e);
            }
            values.put(propertyName, value);
        }
        Data.add(values);
        return this;
    }


    public ResqlitySimpleResponse Execute() throws ResqlityDbException {
        String urlString = Endpoints.INSERT_URL; // URL to call
        InsertModel insertModel = new InsertModel(dbContext.getApiKey(),
                getTableName(),
                getTableSchema(),
                true,
                Data);
        final ResqlitySimpleResponse response = new ResqlitySimpleResponse("", false);

        Runnable insertRequest = () -> {
            try {
                HttpURLConnection urlConnection = ResqlityHelpers.getHttpURLConnection(
                        JsonHelper.Serialize(insertModel),
                        "POST",
                        Endpoints.INSERT_URL,
                        ResqlityHelpers.getDefaultHeaders(dbContext.getApiKey(), getTableName(), getTableSchema()),
                        true,
                        true);
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_CREATED) {
                    ResqlitySimpleResponse t = ResqlityHelpers.getResqlitySimpleResponse(urlConnection.getInputStream());
                    response.setMessage(t.getMessage());
                    response.setSuccess(t.isSuccess());
                } else if (responseCode == HttpsURLConnection.HTTP_BAD_REQUEST) {
                    InputStream errorStream = urlConnection.getErrorStream();
                    String errorMessage = ResqlityHelpers.tryGetHttpErrors(errorStream);
                    response.setSuccess(false);
                    response.setMessage(errorMessage);
                } else {
                    response.setSuccess(false);
                    response.setMessage(null);
                }
                urlConnection.connect();

            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        };

        Thread t = new Thread(insertRequest);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new ResqlityDbException(e.getMessage(), e);
        }
        return response;

    }


}

