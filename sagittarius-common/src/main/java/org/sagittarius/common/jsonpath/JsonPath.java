package org.sagittarius.common.jsonpath;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonElement;

/**
 * Base on com.google.gson or com.fasterxml.jackson
 * <p>
 * <p>JsonPath jsonPath = new JsonPath();</p>
 * <p>jsonPath.value(path, jsonElement or jsonNode)</p>
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

    public JsonElement elementValue(String path, JsonElement jsonElement) {
        String[] elementItems = path.split(this.separator);
        for (String item : elementItems) {
            if (item.equals(ROOT)) {
                continue;
            }
            if (item.contains(SQUARE)) {
                jsonElement = getElement(jsonElement, item.split(SQUARE_SEPARATOR)[0]).getAsJsonArray().get(
                        Integer.valueOf(item.substring(item.indexOf("[") + 1, item.indexOf("]")))
                );
                continue;
            }
            jsonElement = getElement(jsonElement, item);
        }
        return jsonElement;
    }

    private JsonElement getElement(JsonElement jsonElement, String key) {
        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject().get(key);
        }
        return jsonElement;
    }

    public JsonNode nodeValue(String path, JsonNode jsonNode) {
        String[] nodeItems = path.split(this.separator);
        for (String item : nodeItems) {
            if (item.equals(ROOT)) {
                continue;
            }
            if (item.contains(SQUARE)) {
                jsonNode = getNode(jsonNode, item.split(SQUARE_SEPARATOR)[0]).get(
                        Integer.valueOf(item.substring(item.indexOf("[") + 1, item.indexOf("]"))));
                continue;
            }
            jsonNode = getNode(jsonNode, item);
        }
        return jsonNode;
    }

    private JsonNode getNode(JsonNode jsonNode, String key) {
        if (jsonNode.isObject()) {
            return jsonNode.get(key);
        }
        return jsonNode;
    }


}
