package com.tphz.zh_base.http_lib;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tope.http_lib.DataList;
import com.tphz.zh_base.common.LogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DataListSecurityAdapter implements JsonDeserializer<DataList<?>> {
    @Override
    public DataList<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DataList<?> newData = new DataList<>();
        if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            int code = object.get("code").getAsInt();
            JsonElement data = object.get("data");
            Type dataType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];

            if (data.isJsonObject()) {
                newData.setData(context.deserialize(data, dataType));
            } else {
                JsonArray jsonArray = new JsonArray();
                newData.setData(context.deserialize(jsonArray, dataType));
            }
            String message = object.get("message").getAsString();
            newData.setCode(code);
            newData.setMessage(message);
            LogUtil.d("DataSecurityAdapter", "newDataList" + newData);

            return newData;
        } else {
            LogUtil.d("DataSecurityAdapter", "json.isJsonArray() !");
            return newData;
        }
    }
}
