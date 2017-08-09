package org.sagittarius.common.jsoncompare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.sagittarius.common.date.DateUtil;
import org.sagittarius.common.gson.GsonUtil;
import org.sagittarius.common.http.HttpResponseConfig;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Json比对(v2) 适用规则
 * <p>
 * 1 最外层必须是一个object
 * <p>
 * 2 array必须是一个object数组
 * <p>
 * 3 重复字段建议使用输出结果的json path过滤
 * 
 * @author JasonZhang
 *
 */
public class JsonCompareRecorder2 {

	private boolean compareResult;
	private String errorRecorder;

	public JsonCompareRecorder2() {
		super();
	}

	public boolean isCompareResult() {
		return compareResult;
	}

	public String getErrorRecorder() {
		return errorRecorder;
	}

	@Override
	public String toString() {
		return "\nJsonCompareRecorder [compareResult=" + compareResult + ", errorRecorder=" + errorRecorder + "\n]";
	}

	private StringBuilder builder = new StringBuilder();
	private StringBuilder pathBuilder = new StringBuilder();

	private Map<String, Object> filterMap = new HashMap<>();
	private List<JsonCompareCheckPoint> checkPoints = new ArrayList<>();

	/** HTTP RESOINSE 关键字 */
	public static final String KEY_RESPONSE_CODE = "response_code";
	/** json 根路径 关键字 */
	public static final String KEY_ROOT = "root";

	private static final String REGEX_IS_ANY_INTEGER = "^[0-9]+$";
	private static final String REGEX_IS_ANY_DOUBLE = "^[0-9]+.[0-9]+$";
	private static final String REGEX_IS_ANY_STRING = "^..*$";

	/**
	 * 设置过滤规则(entryKey全局过滤)
	 * 
	 * @param entryKey
	 *            json key
	 * @param filter
	 *            过滤枚举
	 */
	public void setRule(String entryKey, FilterEnum filter) {
		if (StringUtils.isEmpty(entryKey) || filter == null) {
			throw new JsonCompareError("Illegal Arguments");
		}
		filterMap.put(entryKey, filter);
	}

	/**
	 * 设置检查规则(除json path外，entryKey全局过滤)
	 * 
	 * @param entryKey
	 *            json key
	 * @param checkPoint
	 *            检查枚举
	 * @param checkKey
	 *            检查关键字
	 */
	public void setRule(String entryKey, CheckEnum checkPoint, String checkKey) {
		if (StringUtils.isEmpty(entryKey) || checkPoint == null) {
			throw new JsonCompareError("Illegal Arguments");
		}
		JsonCompareCheckPoint jsonCompareCheckPoint = new JsonCompareCheckPoint(entryKey, checkPoint, checkKey);
		checkPoints.add(jsonCompareCheckPoint);
	}

	/**
	 * 比较一项
	 * 
	 * @param expect
	 *            预期对象
	 * @param response
	 *            响应对象
	 * @param jsonPath
	 *            比较项的json path
	 */
	public void compareOne(HttpResponseConfig expect, HttpResponseConfig response, String jsonPath) {
		if (expect == null || response == null || StringUtils.isEmpty(jsonPath)) {
			throw new JsonCompareError("Illegal Arguments");
		}

		if (!compareStatusCode(expect, response)) {
			this.compareResult = false;
			this.errorRecorder = builder.toString();
			return;
		}

		pathBuilder.append(jsonPath);

		checkOne(jsonPath, GsonUtil.strToJsonElement(expect.getResponseContent()),
				GsonUtil.strToJsonElement(response.getResponseContent()));

		if (builder.toString().equals("")) {
			this.compareResult = true;
		} else {
			this.compareResult = false;
			this.errorRecorder = builder.toString();
		}
	}

	/**
	 * 比较全部
	 * 
	 * @param expect
	 *            预期对象
	 * @param response
	 *            响应对象
	 */
	public void compareAll(HttpResponseConfig expect, HttpResponseConfig response) {
		if (expect == null || response == null) {
			throw new JsonCompareError("Illegal Arguments");
		}

		if (!compareStatusCode(expect, response)) {
			this.compareResult = false;
			this.errorRecorder = builder.toString();
			return;
		}

		pathBuilder.append(JsonPath.PATH_ROOT);

		compareJsonEquals(GsonUtil.strToJsonElement(expect.getResponseContent()),
				GsonUtil.strToJsonElement(response.getResponseContent()));

		if (builder.toString().equals("")) {
			this.compareResult = true;
		} else {
			this.compareResult = false;
			this.errorRecorder = builder.toString();
		}

	}

	private boolean compareStatusCode(HttpResponseConfig expect, HttpResponseConfig response) {

		if (checkFilter(KEY_RESPONSE_CODE, FilterEnum.IGNORE_RESPONSE_CODE)) {
			return true;
		}
		if (expect.getResponseStatusCode() == response.getResponseStatusCode()) {
			return true;
		} else {
			builder.append(JsonDiffErrorCode.RESPONSE_STATUS_MISS_MATCH)
					.append(setErrorMsg(expect.getResponseStatusCode(), response.getResponseStatusCode()));
			return false;
		}
	}

	private static final String DEBUG_LINE = "\n----------\n";

	private void compareJsonEquals(JsonElement expect, JsonElement other) {

		if (expect.isJsonObject()) {
			pathBuilder.append(JsonPath.PATH_OBJECT);
			checkJsonObj(KEY_ROOT, expect, other);
			return;
		} else {
			throw new JsonCompareError("Must be a JsonObject");
		}

	}

	private void checkJsonArray(String arrayEntryKey, JsonElement expect, JsonElement other) {
		if (checkFilter(arrayEntryKey, FilterEnum.IGNORE_ARRAY_SIZE) || checkPoint(arrayEntryKey,
				CheckEnum.MATCH_JSONDIFF, JsonDiffErrorCode.JSON_ARRAY_SIZE_MISS_MATCH + pathBuilder.toString())) {
			checkArrayOne(expect, other);
			return;
		}

		if (checkFilter(arrayEntryKey, FilterEnum.IGNORE_ARRAY_SIZE)) {
			checkArrayOne(expect, other);
		} else {
			checkArrayAll(expect, other);
		}
	}

	private void checkArrayOne(JsonElement expect, JsonElement other) {
		if (expect.getAsJsonArray().size() == 0 && other.getAsJsonArray().size() == 0) {
			return;
		}
		if (expect.getAsJsonArray().size() == 0 || other.getAsJsonArray().size() == 0) {
			setErrorDebug(JsonDiffErrorCode.JSON_ARRAY_SIZE_MISS_MATCH, pathBuilder, expect.getAsJsonArray().size(),
					other.getAsJsonArray().size());
			return;
		}

		pathBuilder.append("[").append(0).append("]");
		JsonObject subExpect = expect.getAsJsonArray().get(0).getAsJsonObject();
		JsonObject subOther = other.getAsJsonArray().get(0).getAsJsonObject();
		compareJsonEquals(subExpect, subOther);
	}

	private void checkArrayAll(JsonElement expect, JsonElement other) {
		if (expect.getAsJsonArray().size() != other.getAsJsonArray().size()) {
			setErrorDebug(JsonDiffErrorCode.JSON_ARRAY_SIZE_MISS_MATCH, pathBuilder, expect.getAsJsonArray().size(),
					other.getAsJsonArray().size());
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

	private void checkJsonObj(String entryKey, JsonElement expect, JsonElement other) {
		if (checkFilter(entryKey, FilterEnum.IGNORE_VALUE) || checkFilter(entryKey, FilterEnum.IS_JSON_OBJECT)) {
			return;
		}

		Set<Entry<String, JsonElement>> expectSet = expect.getAsJsonObject().entrySet();
		Set<Entry<String, JsonElement>> otherSet = other.getAsJsonObject().entrySet();

		if (checkFilter(entryKey, FilterEnum.IGNORE_OBJECT_SIZE)) {

		} else if (checkPoint(entryKey, CheckEnum.MATCH_JSONDIFF,
				JsonDiffErrorCode.JSON_OBJECT_SIZE_MATCH + pathBuilder.toString())) {

		} else if (expectSet.size() != otherSet.size()) {

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

			builder.append(JsonDiffErrorCode.JSON_OBJECT_SIZE_MATCH).append(pathBuilder)
					.append(setErrorMsg(expectSet.size(), otherSet.size())).append("\n\tMiss:").append(missMatch)
					.append("\n\tUnnecessary:").append(unnecessary).append(DEBUG_LINE);
			return;
		}

		int i = -1;
		String currentPath = pathBuilder.toString();
		for (Entry<String, JsonElement> entry : expectSet) {
			if (checkFilter(entry.getKey(), FilterEnum.IGNORE_VALUE)
					|| checkFilter(entry.getKey(), FilterEnum.IS_JSON_OBJECT)) {
				continue;
			}

			pathBuilder.setLength(0);
			pathBuilder.append(currentPath).toString();
			i++;
			JsonElement jsonElement = other.getAsJsonObject().get(entry.getKey());

			if (jsonElement == null) {
				setPrimitiveKey(i, entry);
				if (checkPoint(entry.getKey(), CheckEnum.MATCH_JSONDIFF,
						JsonDiffErrorCode.KEY_NOT_FOUND + pathBuilder.toString())) {
					continue;
				}
				builder.append(JsonDiffErrorCode.KEY_NOT_FOUND).append(pathBuilder).append(DEBUG_LINE);
				continue;
			}

			if (!GsonUtil.isSameJsonType(entry.getValue(), jsonElement)) {
				setPrimitiveKey(i, entry);
				if (checkPoint(entry.getKey(), CheckEnum.MATCH_JSONDIFF,
						JsonDiffErrorCode.JSON_TYPE_MISS_MATCH + pathBuilder.toString())) {
					continue;
				}
				setErrorDebug(JsonDiffErrorCode.JSON_TYPE_MISS_MATCH, pathBuilder,
						GsonUtil.checkJsonType(entry.getValue()), GsonUtil.checkJsonType(jsonElement));
				continue;
			}

			if (entry.getValue().isJsonPrimitive()) {
				if (checkFilter(entry.getKey(), FilterEnum.IS_JSON_PRIMITIVE)) {
					continue;
				}

				if (!entry.getValue().getAsString().equals(jsonElement.getAsString())) {
					setPrimitiveKey(i, entry);

					if (getValidCheckPoints(entry.getKey()).size() != 0) {

						if (checkPoint(entry.getKey(), CheckEnum.MATCH_JSONDIFF,
								JsonDiffErrorCode.VALUE_MISS_MATCH + pathBuilder.toString())) {
							continue;
						}

						if (!checkPoint(entry.getKey(), getValidCheckPoints(entry.getKey()).get(0).getCheckPoint(),
								jsonElement.getAsString())) {
							setErrorDebug(JsonDiffErrorCode.VALUE_MISS_MATCH, pathBuilder,
									entry.getValue().getAsString(), jsonElement.getAsString());
							continue;
						} else {
							continue;
						}
					}

					setErrorDebug(JsonDiffErrorCode.VALUE_MISS_MATCH, pathBuilder, entry.getValue().getAsString(),
							jsonElement.getAsString());
					continue;
				}
			}

			if (entry.getValue().isJsonObject()) {
				if (checkFilter(entry.getKey(), FilterEnum.IS_JSON_OBJECT)) {
					continue;
				}
				setIndex(i);
				pathBuilder.append(JsonPath.PATH_OBJECT);
				setObjectKey(entry);
				checkJsonObj(entry.getKey(), entry.getValue().getAsJsonObject(), jsonElement.getAsJsonObject());
				continue;
			}

			if (entry.getValue().isJsonArray()) {
				if (checkFilter(entry.getKey(), FilterEnum.IS_JSON_ARRAY)) {
					continue;
				}
				setIndex(i);
				pathBuilder.append(JsonPath.PATH_ARRAY);
				setObjectKey(entry);
				checkJsonArray(entry.getKey(), entry.getValue(), jsonElement);
				continue;
			}

			if (entry.getValue().isJsonNull()) {
				setIndex(i);
				continue;
			}
		}
	}

	private void setErrorDebug(String errorCode, StringBuilder path, Object expect, Object actual) {
		builder.append(errorCode).append(path).append(setErrorMsg(expect, actual)).append(DEBUG_LINE);
	}

	private void setPrimitiveKey(int i, Entry<String, JsonElement> entry) {
		setIndex(i);
		setObjectKey(entry);
	}

	private void setObjectKey(Entry<String, JsonElement> entry) {
		pathBuilder.append("[").append(entry.getKey()).append("]");
	}

	private void setIndex(int i) {
		pathBuilder.append("[").append(i).append("]");
	}

	private String setErrorMsg(Object expect, Object actual) {
		StringBuilder builder = new StringBuilder();
		builder.append("\n").append("\tExpect: ").append(String.valueOf(expect)).append("\n\tActually: ")
				.append(String.valueOf(actual));
		return builder.toString();
	}

	private void checkOne(String jsonPath, JsonElement expect, JsonElement actual) {

		String[] split = jsonPath.split("-");
		String key = "";
		for (int i = 0; i < split.length; i++) {
			if (split[i].contains(JsonPath.PATH_ROOT)) {
				continue;
			}

			if (split[i].contains(JsonPath.PATH_ARRAY.replace("-", "")) && split[i].split("\\[").length > 2) {
				key = split[i].substring(split[i].indexOf("[") + 1, split[i].indexOf("]"));
				int index = Integer
						.valueOf(split[i].substring(split[i].lastIndexOf("[") + 1, split[i].lastIndexOf("]")));
				expect = expect.getAsJsonObject().get(key).getAsJsonArray().get(index);
				actual = actual.getAsJsonObject().get(key).getAsJsonArray().get(index);
				continue;
			}

			if (split[i].contains(JsonPath.PATH_OBJECT.replace("-", "")) && split[i].split("\\[").length > 2) {
				key = split[i].substring(split[i].lastIndexOf("[") + 1, split[i].lastIndexOf("]"));
				expect = expect.getAsJsonObject().get(key);
				actual = actual.getAsJsonObject().get(key);
				if (expect.isJsonPrimitive()) {
					break;
				}
				continue;
			}

		}

		if (!GsonUtil.isSameJsonType(expect, actual)) {
			setErrorDebug(JsonDiffErrorCode.JSON_TYPE_MISS_MATCH, pathBuilder, GsonUtil.checkJsonType(expect),
					GsonUtil.checkJsonType(actual));
			return;
		}

		if (expect.isJsonObject()) {
			if (checkFilter(key, FilterEnum.IS_JSON_OBJECT)) {
				return;
			}
			checkJsonObj(key, expect.getAsJsonObject(), actual.getAsJsonObject());
			return;
		}

		if (expect.isJsonArray()) {
			if (checkFilter(key, FilterEnum.IS_JSON_ARRAY)) {
				return;
			}
			checkJsonArray(key, expect, actual);
			return;
		}

		if (expect.isJsonPrimitive()) {
			if (checkFilter(key, FilterEnum.IS_JSON_PRIMITIVE)) {
				return;
			}

			if (!expect.getAsString().equals(actual.getAsString())) {

				if (getValidCheckPoints(key).size() != 0) {

					if (checkPoint(key, CheckEnum.MATCH_JSONDIFF,
							JsonDiffErrorCode.VALUE_MISS_MATCH + pathBuilder.toString())) {
						return;
					}

					if (!checkPoint(key, getValidCheckPoints(key).get(0).getCheckPoint(), actual.getAsString())) {
						setErrorDebug(JsonDiffErrorCode.VALUE_MISS_MATCH, pathBuilder, expect.getAsString(),
								actual.getAsString());
						return;
					} else {
						return;
					}
				}

				setErrorDebug(JsonDiffErrorCode.VALUE_MISS_MATCH, pathBuilder, expect.getAsString(),
						actual.getAsString());
				return;
			}
		}

		if (expect.isJsonNull()) {
			return;
		}

	}

	private boolean checkFilter(String expectKey, FilterEnum filter) {

		if (filterMap.containsKey(expectKey) && filterMap.get(expectKey).equals(filter)) {
			return true;
		}

		return false;
	}

	private boolean haveCheckPoint(String entryKey, CheckEnum checkPoint, String checkKey) {
		if (checkPoints.contains(new JsonCompareCheckPoint(entryKey, checkPoint, checkKey))) {
			return true;
		}
		return false;
	}

	// XXX 全局有效，重复key不能区分
	private boolean checkPoint(String entryKey, CheckEnum checkPoint, String actualValue) {

		switch (checkPoint) {

		case IS_ANY_INTEGER:
			return actualValue != null && (actualValue.matches(REGEX_IS_ANY_INTEGER));

		case IS_ANY_DOUBLE:
			return actualValue != null && (actualValue.matches(REGEX_IS_ANY_DOUBLE));

		case IS_ANY_STRING:
			return actualValue != null && (actualValue.matches(REGEX_IS_ANY_STRING));

		case IS_TIMESTEMP:
			return actualValue != null && (actualValue.matches(DateUtil.YYYY_MM_DD_HH_MM_SS));

		case MATCH_JSONDIFF:
			return haveCheckPoint(entryKey, checkPoint, actualValue);

		case MATCH_REGEX:
			return actualValue != null && (actualValue.matches(getValidCheckPoints(entryKey).get(0).getCheckKey()));

		case MATCH_RULE_REGEX:
			if (haveCheckPoint(entryKey, checkPoint, RuleRegexConstant.IGNORE_VALUE)) {
				return true;
			}

			if (haveCheckPoint(entryKey, checkPoint, RuleRegexConstant.IS_NUMBER)) {
				if (actualValue.matches(REGEX_IS_ANY_INTEGER) || actualValue.matches(REGEX_IS_ANY_DOUBLE)) {
					return true;
				}
			}

			if (haveCheckPoint(entryKey, checkPoint, RuleRegexConstant.IS_STRING)) {
				if (actualValue.matches(REGEX_IS_ANY_STRING)) {
					return true;
				}
			}

			return false;

		default:
			return false;
		}
	}

	private List<JsonCompareCheckPoint> getValidCheckPoints(String entryKey) {
		List<JsonCompareCheckPoint> list = new ArrayList<>();
		for (JsonCompareCheckPoint jsonCompareCheckPoint : checkPoints) {
			if (jsonCompareCheckPoint.getEntryKey().equals(entryKey)) {
				list.add(jsonCompareCheckPoint);
			}
		}
		return list;
	}

}
