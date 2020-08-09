package com.resqlity.orm.tasks;

import com.resqlity.orm.consts.Endpoints;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

import javax.net.ssl.HttpsURLConnection;

public class InsertRequestAsyncTask implements Runnable {

    private String data;
    private String apiKey;
    private String tableName;
    private String tableSchema;

    public InsertRequestAsyncTask(String data, String apiKey, String tableName, String tableSchema) {
        this.data = data;
        this.apiKey = apiKey;
        this.tableName = tableName;
        this.tableSchema = tableSchema;
    }



    @Override
    public void run() {
        OutputStream out = null;
        String response = "";
        try {
            URL url = new URL(Endpoints.INSERT_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("ApiKey", apiKey);
            urlConnection.setRequestProperty("TableName", tableName);
            urlConnection.setRequestProperty("TableSchema", tableSchema);
            urlConnection.setRequestProperty("Content-Type","application/json");
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(data);
            writer.flush();
            writer.close();
            os.close();

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_CREATED || responseCode==HttpsURLConnection.HTTP_BAD_REQUEST) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
            urlConnection.connect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
