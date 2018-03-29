package org.sagittarius.common;

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
    private static final String SQUARE_L = "[";
    private static final String SQUARE_R = "]";
    private static final String SQUARE_SEPARATOR = "\\[";

    public JsonPath() {
        this.separator = Separator.DOT.getSeparator();
    }

    public JsonPath(Separator separator) {
        this.separator = separator.getSeparator();
    }

    private void verifyPath(String path) {
        if (!path.startsWith(ROOT)) {
            throw new RuntimeException("path: " + path + " must start with '$'");
        }
        if (path.indexOf(ROOT) != path.lastIndexOf(ROOT)) {
            throw new RuntimeException("path: " + path + " must contains one '$'");
        }
    }

    public JsonElement elementValue(String path, JsonElement jsonElement) {
        verifyPath(path);
        String[] elementItems = path.split(this.separator);
        for (String item : elementItems) {
            if (item.equals(ROOT)) {
                continue;
            }
            if (item.contains(SQUARE_L)) {
                jsonElement = getElement(jsonElement, item.split(SQUARE_SEPARATOR)[0], true).getAsJsonArray().get(
                        Integer.valueOf(item.substring(item.indexOf(SQUARE_L) + 1, item.indexOf(SQUARE_R)))
                );
                continue;
            }
            jsonElement = getElement(jsonElement, item, false);
        }
        return jsonElement;
    }

    private JsonElement getElement(JsonElement jsonElement, String key, Boolean isList) {
        if (jsonElement.isJsonObject()) {
            if (jsonElement.getAsJsonObject().keySet().contains(key)) {
                return jsonElement.getAsJsonObject().get(key);
            }
            throw new RuntimeException("JsonElement: " + jsonElement + " not have key: " + key);
        }
        if (jsonElement.isJsonArray()) {
            if (isList) {
                return jsonElement;
            }
            throw new RuntimeException("list should use '[]'");
        }

        throw new RuntimeException("Can't find key: " + key + " in primary value: " + jsonElement);
    }

    public JsonNode nodeValue(String path, JsonNode jsonNode) {
        verifyPath(path);
        String[] nodeItems = path.split(this.separator);
        for (String item : nodeItems) {
            if (item.equals(ROOT)) {
                continue;
            }
            if (item.contains(SQUARE_L)) {
                JsonNode tmp = getNode(jsonNode, item.split(SQUARE_SEPARATOR)[0], true);
                jsonNode = tmp.path(Integer.valueOf(item.substring(item.indexOf(SQUARE_L) + 1, item.indexOf(SQUARE_R))));
                if (jsonNode.isMissingNode()) {
                    throw new RuntimeException("JsonNode: " + tmp + " index out of range: length: "
                            + "index: " + item.substring(item.indexOf(SQUARE_L) + 1, item.indexOf(SQUARE_R)));
                }
                continue;
            }
            jsonNode = getNode(jsonNode, item, false);
        }
        return jsonNode;
    }

    private JsonNode getNode(JsonNode jsonNode, String key, boolean isList) {
        if (jsonNode.isObject()) {
            if (jsonNode.path(key).isMissingNode()) {
                throw new RuntimeException("JsonNode: " + jsonNode + " not have key: " + key);
            }
            return jsonNode.path(key);
        }

        if (jsonNode.isArray()) {
            if (isList) {
                return jsonNode;
            }
            throw new RuntimeException("list should use '[]'");
        }
        throw new RuntimeException("Can't find key: " + key + " in primary value: " + jsonNode);
    }


}
