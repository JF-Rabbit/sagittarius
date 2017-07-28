package org.sagittarius.common.jsoncompare;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.sagittarius.common.gson.GsonUtil;
import org.sagittarius.common.http.HttpResponseConfig;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonCompareRecorder {

	private boolean compareResult;
	private String errorRecorder;

	public JsonCompareRecorder() {
		super();
	}

	public JsonCompareRecorder(boolean compareResult, String errorRecorder) {
		super();
		this.compareResult = compareResult;
		this.errorRecorder = errorRecorder;
	}

	public boolean isCompareResult() {
		return compareResult;
	}

	public void setCompareResult(boolean compareResult) {
		this.compareResult = compareResult;
	}

	public String getErrorRecorder() {
		return errorRecorder;
	}

	public void setErrorRecorder(String errorRecorder) {
		this.errorRecorder = errorRecorder;
	}

	@Override
	public String toString() {
		return "JsonCompareRecorder [compareResult=" + compareResult + ", errorRecorder=" + errorRecorder + "\n]";
	}

	private StringBuilder builder = new StringBuilder();
	private StringBuilder pathBuilder = new StringBuilder();

	public void compare(HttpResponseConfig expect, HttpResponseConfig response, Map<String, IgnoreRuleEnum> ignoreMap) {

		if (!compareStatusCode(expect, response)) {
			this.compareResult = false;
			return;
		}

		pathBuilder.append(PATH_ROOT);

		compareJsonEquals(GsonUtil.strToJsonElement(expect.getContent()),
				GsonUtil.strToJsonElement(response.getContent()));

		if (builder.toString().equals("")) {
			this.compareResult = true;
		} else {
			this.compareResult = false;
			this.errorRecorder = builder.toString();
		}

	}

	private boolean compareStatusCode(HttpResponseConfig expect, HttpResponseConfig response) {
		if (expect.getStatusCode() == response.getStatusCode()) {
			return true;
		} else {
			return false;
		}
	}

	private static final String KEY_NOT_FOUND = "\nKEY_NOT_FOUND:\t";
	private static final String VALUE_MISS_MATCH = "\nVALUE_MISS_MATCH:\n";
	private static final String JSON_TYPE_MISS_MATCH = "\nJSON_TYPE_MISS_MATCH:\n";
	private static final String JSON_OBJECT_SIZE_MATCH = "\nJSON_OBJECT_SIZE_MATCH:\n";
	private static final String JSON_ARRAY_SIZE_MISS_MATCH = "\nJSON_ARRAY_SIZE_MISS_MATCH:\n";

	private static final String PATH_ROOT = "root";
	private static final String PATH_ARRAY = "-array";
	private static final String PATH_OBJECT = "-obj";
	private static final String PATH_PRIMITIVE = "-primitive";
	private static final String PATH_KEY = "-key";

	private static final String DEBUG_LINE = "\n----------\n";

	private void compareJsonEquals(JsonElement expect, JsonElement other) {

		if (!GsonUtil.isSameJsonType(expect, other)) {
			builder.append(JSON_TYPE_MISS_MATCH).append(pathBuilder).append("\n").append(
					setErrorMsg(GsonUtil.checkJsonType(expect).toString(), GsonUtil.checkJsonType(other).toString()))
					.append(DEBUG_LINE);
			return;
		}

		if (expect.isJsonPrimitive()) {
			pathBuilder.append(PATH_PRIMITIVE);
			if (!expect.getAsString().equals(other.getAsString())) {
				builder.append(VALUE_MISS_MATCH).append(pathBuilder).append("\n")
						.append(setErrorMsg(expect.getAsString(), other.getAsString())).append(DEBUG_LINE);
			}
			return;
		}

		if (expect.isJsonObject()) {
			pathBuilder.append(PATH_OBJECT);
			checkJsonObj(expect, other);
			return;
		}

		if (expect.isJsonArray()) {
			pathBuilder.append(PATH_ARRAY);
			checkJsonArray(expect, other);
			return;
		}

		if (expect.isJsonNull()) {
			return;
		}

	}

	private void checkJsonArray(JsonElement expect, JsonElement other) {
		if (expect.getAsJsonArray().size() != other.getAsJsonArray().size()) {
			builder.append(JSON_ARRAY_SIZE_MISS_MATCH).append(pathBuilder)
					.append(setErrorMsg(String.valueOf(expect.getAsJsonArray().size()),
							String.valueOf(other.getAsJsonArray().size())))
					.append(DEBUG_LINE);
			return;
		}

		for (int i = 0; i < expect.getAsJsonArray().size(); i++) {
			String currentPath = pathBuilder.toString();

			pathBuilder.append("[").append(i).append("]");
			JsonObject subExpect = expect.getAsJsonArray().get(i).getAsJsonObject();
			JsonObject subOther = other.getAsJsonArray().get(i).getAsJsonObject();
			compareJsonEquals(subExpect, subOther);
			pathBuilder.setLength(0);
			pathBuilder.append(currentPath);
		}
	}

	private void checkJsonObj(JsonElement expect, JsonElement other) {
		Set<Entry<String, JsonElement>> expectSet = expect.getAsJsonObject().entrySet();
		Set<Entry<String, JsonElement>> otherSet = other.getAsJsonObject().entrySet();

		if (expectSet.size() != otherSet.size()) {
			StringBuilder missMatch = new StringBuilder();
			StringBuilder unnecessary = new StringBuilder();
			Set<String> expectKeySet = expect.getAsJsonObject().keySet();
			Set<String> otherKeySet = other.getAsJsonObject().keySet();
			Set<String> commonKeySet = new HashSet<>();

			expectKeySet.forEach(arg -> {
				if (otherKeySet.contains(arg))
					commonKeySet.add(arg);
			});
			expectKeySet.removeAll(commonKeySet);
			otherKeySet.removeAll(commonKeySet);
			missMatch.append(expectKeySet);
			unnecessary.append(otherKeySet);

			builder.append(JSON_OBJECT_SIZE_MATCH).append(pathBuilder)
					.append(setErrorMsg(String.valueOf(expectSet.size()), String.valueOf(otherSet.size())))
					.append("\n\tMiss:").append(missMatch).append("\n\tUnnecessary:").append(unnecessary)
					.append(DEBUG_LINE);
			return;
		}

		int i = 0;
		for (Entry<String, JsonElement> entry : expectSet) {

			String currentPath = pathBuilder.toString();
			pathBuilder.append("[").append(i).append("]");
			i++;
			JsonElement jsonElement = other.getAsJsonObject().get(entry.getKey());

			if (jsonElement == null) {
				builder.append(KEY_NOT_FOUND).append("\n").append(pathBuilder).append(PATH_KEY).append("[")
						.append(entry.getKey()).append("]\n").append(DEBUG_LINE);
				resetJsonObjPath(currentPath);
				continue;
			}

			if (!GsonUtil.isSameJsonType(entry.getValue(), jsonElement)) {
				builder.append(JSON_TYPE_MISS_MATCH).append(pathBuilder)
						.append(setErrorMsg(GsonUtil.checkJsonType(entry.getValue()).toString(),
								GsonUtil.checkJsonType(jsonElement).toString()))
						.append(DEBUG_LINE);
				resetJsonObjPath(currentPath);
				continue;
			}

			if (entry.getValue().isJsonPrimitive()) {
				pathBuilder.append(PATH_PRIMITIVE).append("[").append(entry.getKey()).append("]");
				if (!entry.getValue().getAsString().equals(jsonElement.getAsString())) {
					builder.append(VALUE_MISS_MATCH).append(pathBuilder)
							.append(setErrorMsg(entry.getValue().getAsString(), jsonElement.getAsString()))
							.append(DEBUG_LINE);
					resetJsonObjPath(currentPath);
					continue;
				}
			}

			if (entry.getValue().isJsonObject()) {
				pathBuilder.append(PATH_OBJECT).append("[").append(entry.getKey()).append("]");
				checkJsonObj(entry.getValue().getAsJsonObject(), jsonElement.getAsJsonObject());
				resetJsonObjPath(currentPath);
				continue;
			}

			if (entry.getValue().isJsonArray()) {
				pathBuilder.append(PATH_ARRAY).append("[").append(entry.getKey()).append("]");
				checkJsonArray(entry.getValue(), jsonElement);
				resetJsonObjPath(currentPath);
				continue;
			}

			if (entry.getValue().isJsonNull()) {
				resetJsonObjPath(currentPath);
				continue;
			}
		}
	}

	private void resetJsonObjPath(String currentPath) {
		pathBuilder.setLength(0);
		pathBuilder.append(currentPath);
	}

	private String setErrorMsg(String expect, String result) {
		StringBuilder builder = new StringBuilder();
		builder.append("\n").append("\tExpect: ").append(expect).append("\n\tActually: ").append(result);
		return builder.toString();
	}
}
