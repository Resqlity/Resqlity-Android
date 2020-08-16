package com.resqlity.orm.helpers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.internal.LinkedTreeMap;
import com.resqlity.orm.models.responses.ResqlityError;
import com.resqlity.orm.models.responses.ResqlityErrorResponse;
import com.resqlity.orm.models.responses.ResqlityResponse;
import com.resqlity.orm.models.responses.ResqlitySimpleResponse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ResqlityHelpers {

    /**
     * @param errorStream Http Error Stream
     * @return Formatted Error Message
     * @throws IOException
     * @throws IllegalAccessException
     */
    public static String tryGetHttpErrors(InputStream errorStream) throws IOException, IllegalAccessException {
        String jsonResponse = "";
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(errorStream));
        while ((line = br.readLine()) != null) {
            jsonResponse += line;
        }
        ResqlityErrorResponse resqlityErrorResponse = JsonHelper.Deserialize(jsonResponse, ResqlityErrorResponse.class);
        return resqlityErrorResponse.getMessage();
    }

    /**
     * @param inputStream Http Input Stream
     * @return ResqlitySimpleResponse
     * @throws IOException
     */
    public static ResqlitySimpleResponse getResqlitySimpleResponse(InputStream inputStream) throws IOException {
        String jsonResponse = "";
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = br.readLine()) != null) {
            jsonResponse += line;
        }
        return (ResqlitySimpleResponse) JsonHelper.Deserialize(jsonResponse, ResqlitySimpleResponse.class);
    }

    /**
     * @param stream Http Input Stream
     * @param tClass Data Class
     * @param <T>    Data Type
     * @return ResqlityResponse with specified type
     * @throws IOException
     */
    public static <T> ResqlityResponse<T> getResqlityResponse(InputStream stream, Class<?> tClass) throws IOException {
        String jsonResponse = "";
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        while ((line = br.readLine()) != null) {
            jsonResponse += line;
        }

        return (ResqlityResponse<T>) JsonHelper.Deserialize(jsonResponse, tClass);
    }

    /**
     * @param apiKey      Api Key
     * @param tableName   Base Table Name
     * @param tableSchema Base Table Schema
     * @return Returns http headers
     */
    public static Map<String, String> getDefaultHeaders(String apiKey, String tableName, String tableSchema) {
        return getDefaultHeaders(apiKey, tableName, tableSchema, "application/json");
    }

    /**
     * @param apiKey      Api Key
     * @param tableName   Base Table Name
     * @param tableSchema Base Table Schema
     * @param contentType Data Content Type
     * @return Default Http Headers
     */
    public static Map<String, String> getDefaultHeaders(String apiKey, String tableName, String tableSchema, String contentType) {
        Map<String, String> headers = new Hashtable<>();
        headers.put("ApiKey", apiKey);
        headers.put("TableName", tableName);
        headers.put("TableSchema", tableSchema);
        headers.put("Content-Type", contentType);
        return headers;
    }

    /**
     * @param data     Data To Post
     * @param method   Http Method
     * @param uri      Url
     * @param headers  Headers
     * @param doInput  Can Input
     * @param doOutput Can Output
     * @return HttpUrlConnection
     * @throws IOException
     */
    public static HttpURLConnection getHttpURLConnection(String data, String method, String uri, Map<String, String> headers, boolean doInput, boolean doOutput) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(method);
        urlConnection.setDoInput(doInput);
        urlConnection.setDoOutput(doOutput);
        for (Map.Entry<String, String> entry :
                headers.entrySet()) {
            urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
        }
        OutputStream os = urlConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, StandardCharsets.UTF_8));
        writer.write(data);
        writer.flush();
        writer.close();
        os.close();
        return urlConnection;
    }
}
