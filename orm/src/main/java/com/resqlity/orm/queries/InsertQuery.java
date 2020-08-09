package com.resqlity.orm.queries;

import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.consts.Endpoints;
import com.resqlity.orm.helpers.JsonHelper;
import com.resqlity.orm.models.querymodels.InsertModel;
import com.resqlity.orm.models.responses.ResqlitySimpleResponse;

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
        final ResqlitySimpleResponse response = new ResqlitySimpleResponse("", false);

        Runnable insertRequest = () -> {
            OutputStream out = null;
            String jsonResponse = "";
            try {
                URL url = new URL(Endpoints.INSERT_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("ApiKey", dbContext.getApiKey());
                urlConnection.setRequestProperty("TableName", getTableName());
                urlConnection.setRequestProperty("TableSchema", getTableSchema());
                urlConnection.setRequestProperty("Content-Type", "application/json");
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(data);
                writer.flush();
                writer.close();
                os.close();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_CREATED || responseCode == HttpsURLConnection.HTTP_BAD_REQUEST) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        jsonResponse += line;
                    }
                    ResqlitySimpleResponse t = (ResqlitySimpleResponse) JsonHelper.Deserialize(jsonResponse, response.getClass());
                    response.setMessage(t.getMessage());
                    response.setSuccess(t.isSuccess());
                } else {
                    jsonResponse = "";
                }
                urlConnection.connect();

            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        };

        Thread t = new Thread(insertRequest);
        t.start();
        t.join();
        if (!response.isSuccess())
            throw new Exception(response.getMessage());


    }

}

