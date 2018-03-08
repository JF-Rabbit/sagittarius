package org.sagittarius.common.jsonpath;

import com.google.gson.JsonElement;

/**
 * Base on com.google.gson
 * <p>
 * <p>JsonPath jsonPath = new JsonPath();</p>
 * <p>jsonPath.value(path, jsonElement)</p>
 * <p>path support grammar:</p>
 * <p>$.key1,key2       =>  {"key1" : {"key2" : value}}</p>
 * <p>$.key1[0],key3    =>  {"key1" : [{"key3":value}, {...}]}</p>
 * <p>$.[0],key1,key4   =>  [{"key1" : {"key4":value}}, {...}]</p>
 */
public class JsonPath {

    private String separator;

    public enum Separator {
        DOT("[.]"),
        STAR("\\*"),
        L_STR("\\|");

        private String separator;

        public String getSeparator() {
            return separator;
        }

        Separator(String separator) {
            this.separator = separator;
        }
    }

    private static final String ROOT = "$";
    private static final String SQUARE = "[";
    private static final String SQUARE_SEPARATOR = "\\[";

    public JsonPath() {
        this.separator = Separator.DOT.getSeparator();
    }

    public JsonPath(Separator separator) {
        this.separator = separator.getSeparator();
    }

    public JsonElement value(String path, JsonElement jsonElement) {
        String[] items = path.split(this.separator);
        for (String item : items) {
            if (item.equals(ROOT)) {
                continue;
            }
            if (item.contains(SQUARE)) {
                jsonElement = get(jsonElement, item.split(SQUARE_SEPARATOR)[0]).getAsJsonArray().get(
                        Integer.valueOf(item.substring(item.indexOf("[") + 1, item.indexOf("]")))
                );
                continue;
            }
            jsonElement = get(jsonElement, item);
        }
        return jsonElement;
    }

    private JsonElement get(JsonElement jsonElement, String key) {
        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject().get(key);
        }
        return jsonElement;
    }


}
