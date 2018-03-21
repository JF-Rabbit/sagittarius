package org.sagittarius.common.gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonUtil {

    public static String jsonObjToStr(JsonObject jsonObject) {
        return new Gson().toJson(jsonObject);
    }

    /**
     * Json格式字符串格式化
     *
     * @param jsonObject Json对象
     * @return Json对象格式化输出
     */
    public static String jsonObjFormat(JsonObject jsonObject) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject);
    }

    public static String jsonStrFormat(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Param is empty");
        }
        JsonElement element = strToJsonElement(str);
        if (element.isJsonObject()) {
            return "\n" + jsonObjFormat(element.getAsJsonObject()) + "\n";
        } else {
            throw new IllegalArgumentException(str + "\t Is not a Json Object");
        }
    }

    public static JsonElement strToJsonElement(String str) {
        return new JsonParser().parse(str);
    }

    public static JsonTypeEnum checkJsonType(JsonElement jsonElement) {
        if (jsonElement == null) {
            return null;
        }
        if (jsonElement.isJsonObject()) {
            return JsonTypeEnum.OBJECT;
        }
        if (jsonElement.isJsonArray()) {
            return JsonTypeEnum.ARRAY;
        }
        if (jsonElement.isJsonPrimitive()) {
            return JsonTypeEnum.PRIMITIVE;
        }
        if (jsonElement.isJsonNull()) {
            return JsonTypeEnum.NULL;
        }
        return null;
    }

    public static boolean isSameJsonType(JsonElement expect, JsonElement other) {
        return expect != null && checkJsonType(expect).equals(checkJsonType(other));
    }

    /**
     * 从文件当中读取并专成JsonElement对象
     *
     * @param jsonFilePath Json文件路径
     * @return JsonElement
     */
    public static JsonElement getJsonObjFromJsonFile(String jsonFilePath) {
        BufferedReader reader = null;
        JsonElement jsonElement = null;
        try {
            reader = new BufferedReader(new FileReader(new File(jsonFilePath)));
            jsonElement = new Gson().fromJson(reader, JsonElement.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonElement;
    }
}
