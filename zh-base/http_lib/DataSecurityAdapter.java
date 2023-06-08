package com.tope.http_lib;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tope.common.LogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class DataSecurityAdapter implements JsonDeserializer<Data<?>> {
    @Override
    public Data<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Data<?> newData = new Data<>();
        if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            int code = object.get("code").getAsInt();
            JsonElement data = object.get("data");
            Type dataType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];

            if (data.isJsonObject()) {
                newData.setData(context.deserialize(data, dataType));
            }else{
                JsonObject jsonObject =  new JsonObject();
                newData.setData(context.deserialize(jsonObject, dataType));
            }
            String message = object.get("message").getAsString();
            newData.setCode(code);
            newData.setMessage(message);
            LogUtil.d("DataSecurityAdapter", "newData" + newData);

            return newData;
        } else {
            LogUtil.d("DataSecurityAdapter", "json.isJsonArray() !");
            return newData;
        }
    }
}
