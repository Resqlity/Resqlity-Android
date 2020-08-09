package com.resqlity.orm.helpers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.internal.LinkedTreeMap;
import com.resqlity.orm.models.responses.ResqlityError;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResqlityHelpers {

    public static String ParseErrors(Object errors) throws IllegalAccessException {
        LinkedTreeMap<String, ArrayList<String>> linkedTreeMap = (LinkedTreeMap<String, ArrayList<String>>) errors;
        StringBuilder globalBuilder = new StringBuilder();
        for (Map.Entry<String, ArrayList<String>> entry :
                linkedTreeMap.entrySet()) {
            List<String> propertyErrors = new ArrayList<>();
            StringBuilder sb = new StringBuilder();

            for (String item :
                    entry.getValue()) {
                sb.append(item);
                sb.append("\t");
            }
            globalBuilder.append(sb.toString());
            globalBuilder.append("\n");
        }
        return globalBuilder.toString();
    }
}
