package com.tphz.zh_base.http_lib;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tope.http_lib.Data;
import com.tphz.zh_base.common.LogUtil;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
