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
 * Json比对 适用规则
 * <p>
 * 1 最外层必须是一个object
 * <p>
 * 2 array必须是一个object数组
 * <p>
 * 3 重复字段必须用输入结果的jsonPath过滤
 * 
 * @author jasonzhang 2017年7月29日 下午6:34:42
 *
 */
public class JsonCompareRecorder implements JsonDiffErrorCode, JsonPath, RuleRegexConstant {

	private boolean compareResult;
	private String errorRecorder;
	private List<RuleEnum> ignoreGlobalList = new ArrayList<>();
	private List<JsonDiff> ignoreJsonDiffList = new ArrayList<>();
	private Map<String, RuleEnum> ruleMap = new HashMap<>();
	private Map<String, String> regexMap = new HashMap<>();

	public JsonCompareRecorder() {
		super();
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
		return "\nJsonCompareRecorder [compareResult=" + compareResult + ", errorRecorder=" + errorRecorder + "\n]";
	}

	/** 适用于 IGNORE_RESPONSE_CODE, IGNORE_OBJECT_SIZE, IGNORE_ARRAY_SIZE，全局忽略 */
	public void setOption(RuleEnum ruleEnum) {
		ignoreGlobalList.add(ruleEnum);
	}

	/** 根据输出的结果信息，录入一个JsonDiff对象过滤，精确忽略 */
	public void setOption(JsonDiff jsonDiff) {
		ignoreJsonDiffList.add(jsonDiff);
	}

	/** 正字表达式匹配过滤，全局忽略 */
	public void setOption(String regexKey, String regexFormat) {
		regexMap.put(regexKey, regexFormat);
	}

	/**
	 * 适用于 IGNORE_KEY_VALUE, CHECK_ARRAY_NO1, IS_ANY_INTEGER, IS_ANY_STRING,
	 * IS_TIMESTEMP, IS_JSON_ARRAY，全局忽略
	 */
	public void setOption(String entryKey, RuleEnum ruleEnum) {
		ruleMap.put(entryKey, ruleEnum);
	}

	private StringBuilder builder = new StringBuilder();
	private StringBuilder pathBuilder = new StringBuilder();

	public void compare(HttpResponseConfig expect, HttpResponseConfig response) {

		if (!compareStatusCode(expect, response)) {
			this.compareResult = false;
			this.errorRecorder = builder.toString();
			return;
		}

		pathBuilder.append(PATH_ROOT);

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
		if (checkIgnoreGlobalList(RuleEnum.IGNORE_RESPONSE_CODE)) {
			return true;
		}
		if (expect.getResponseStatusCode() == response.getResponseStatusCode()) {
			return true;
		} else {
			builder.append(RESPONSE_STATUS_MISS_MATCH)
					.append(setErrorMsg(expect.getResponseStatusCode(), response.getResponseStatusCode()));
			return false;
		}
	}

	private static final String DEBUG_LINE = "\n----------\n";

	private void compareJsonEquals(JsonElement expect, JsonElement other) {

		// if (!GsonUtil.isSameJsonType(expect, other)) {
		// builder.append(JSON_TYPE_MISS_MATCH).append(pathBuilder).append("\n")
		// .append(setErrorMsg(GsonUtil.checkJsonType(expect),
		// GsonUtil.checkJsonType(other))).append(DEBUG_LINE);
		// return;
		// }
		//
		// if (expect.isJsonPrimitive()) {
		// if (!expect.getAsString().equals(other.getAsString())) {
		// builder.append(VALUE_MISS_MATCH).append(pathBuilder).append("\n").append(setErrorMsg(expect,
		// other)).append(DEBUG_LINE);
		// }
		// return;
		// }

		if (expect.isJsonObject()) {
			pathBuilder.append(PATH_OBJECT);
			checkJsonObj(expect, other);
			return;
		} else {
			throw new JsonCompareError("");
		}

		// if (expect.isJsonArray()) {
		// pathBuilder.append(PATH_ARRAY);
		// checkJsonArray(expect, other);
		// return;
		// }
		//
		// if (expect.isJsonNull()) {
		// return;
		// }

	}

	private void checkJsonArray(String arrayEntryKey, JsonElement expect, JsonElement other) {
		if (checkRuleMap(arrayEntryKey, RuleEnum.IGNORE_VALUE)) {
			return;
		}
		if (checkIgnoreGlobalList(RuleEnum.IGNORE_ARRAY_SIZE)) {
			checkArrayOne(expect, other);
			return;
		} else if (checkIgnoreJsonPath(new JsonDiff(JSON_ARRAY_SIZE_MISS_MATCH, pathBuilder.toString()))) {
			checkArrayOne(expect, other);
			return;
		} else if (expect.getAsJsonArray().size() != other.getAsJsonArray().size()) {
			setErrorDebug(JSON_ARRAY_SIZE_MISS_MATCH, pathBuilder, expect.getAsJsonArray().size(),
					other.getAsJsonArray().size());
			return;
		}

		if (expect.getAsJsonArray().size() == 0 || other.getAsJsonArray().size() == 0) {
			throw new JsonCompareError("");
		}

		if (checkRuleMap(arrayEntryKey, RuleEnum.CHECK_ARRAY_NO1)) {
			checkArrayOne(expect, other);
		} else {
			checkArrayAll(expect, other);
		}
	}

	private void checkArrayOne(JsonElement expect, JsonElement other) {
		pathBuilder.append("[").append(0).append("]");
		JsonObject subExpect = expect.getAsJsonArray().get(0).getAsJsonObject();
		JsonObject subOther = other.getAsJsonArray().get(0).getAsJsonObject();
		compareJsonEquals(subExpect, subOther);
	}

	private void checkArrayAll(JsonElement expect, JsonElement other) {
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

		if (checkIgnoreGlobalList(RuleEnum.IGNORE_OBJECT_SIZE)) {

		} else if (checkIgnoreJsonPath(new JsonDiff(JSON_OBJECT_SIZE_MATCH, pathBuilder.toString()))) {

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

			builder.append(JSON_OBJECT_SIZE_MATCH).append(pathBuilder)
					.append(setErrorMsg(expectSet.size(), otherSet.size())).append("\n\tMiss:").append(missMatch)
					.append("\n\tUnnecessary:").append(unnecessary).append(DEBUG_LINE);
			return;
		}

		int i = -1;
		String currentPath = pathBuilder.toString();
		for (Entry<String, JsonElement> entry : expectSet) {

			pathBuilder.setLength(0);
			pathBuilder.append(currentPath).toString();
			i++;
			JsonElement jsonElement = other.getAsJsonObject().get(entry.getKey());

			if (jsonElement == null) {
				setPrimitiveKey(i, entry);
				if (checkIgnoreJsonPath(new JsonDiff(KEY_NOT_FOUND, pathBuilder.toString()))) {
					continue;
				}
				builder.append(KEY_NOT_FOUND).append(pathBuilder).append(DEBUG_LINE);
				continue;
			}

			if (!GsonUtil.isSameJsonType(entry.getValue(), jsonElement)) {
				setIndex(i);
				if (checkIgnoreJsonPath(new JsonDiff(JSON_TYPE_MISS_MATCH, pathBuilder.toString()))) {
					continue;
				}
				setErrorDebug(JSON_TYPE_MISS_MATCH, pathBuilder, GsonUtil.checkJsonType(entry.getValue()),
						GsonUtil.checkJsonType(jsonElement));
				continue;
			}

			if (checkRuleMap(entry.getKey(), RuleEnum.IGNORE_VALUE)) {
				continue;
			}

			if (entry.getValue().isJsonPrimitive()) {

				if (checkRuleConstant(entry.getValue().getAsString(), IGNORE_VALUE)) {
					continue;
				}

				if (checkRuleMap(entry.getKey())) {
					boolean isError = false;
					switch (ruleMap.get(entry.getKey())) {
					case IS_ANY_INTEGER:
						// XXX 负数 double
						if (!StringUtils.isNumeric(entry.getValue().getAsString()))
							isError = true;
						break;
					case IS_ANY_STRING:
						if (StringUtils.isEmpty(entry.getValue().getAsString()))
							isError = true;
						break;
					case IS_TIMESTEMP:
						if (!entry.getValue().getAsString().matches(DateUtil.YYYY_MM_DD_HH_MM_SS))
							isError = true;
						break;

					default:
						break;
					}

					if (isError) {
						setPrimitiveKey(i, entry);
						if (checkIgnoreJsonPath(new JsonDiff(VALUE_MISS_REGEX, pathBuilder.toString()))) {
							continue;
						}
						setErrorDebug(VALUE_MISS_REGEX, pathBuilder, entry.getValue().getAsString(), jsonElement.getAsString());
					}

					continue;
				}

				if (checkRegexMap(entry.getKey(), entry.getValue().getAsString())) {
					continue;
				}

				if (entry.getValue().getAsString().equals(IS_NUMBER)) {
					if (!StringUtils.isNumeric(entry.getValue().getAsString())) {
						setPrimitiveKey(i, entry);
						if (checkIgnoreJsonPath(new JsonDiff(VALUE_MISS_REGEX, pathBuilder.toString()))) {
							continue;
						}
						setErrorDebug(VALUE_MISS_REGEX, pathBuilder, entry.getValue().getAsString(), jsonElement.getAsString());
					}
					continue;
				}

				if (entry.getValue().getAsString().equals(IS_STRING)) {
					if (StringUtils.isEmpty(entry.getValue().getAsString())) {
						setPrimitiveKey(i, entry);
						if (checkIgnoreJsonPath(new JsonDiff(VALUE_MISS_REGEX, pathBuilder.toString()))) {
							continue;
						}
						setErrorDebug(VALUE_MISS_REGEX, pathBuilder, entry.getValue().getAsString(), jsonElement.getAsString());
					}
					continue;
				}

				if (!entry.getValue().getAsString().equals(jsonElement.getAsString())) {
					setPrimitiveKey(i, entry);
					if (checkIgnoreJsonPath(new JsonDiff(VALUE_MISS_MATCH, pathBuilder.toString()))) {
						continue;
					}
					setErrorDebug(VALUE_MISS_MATCH, pathBuilder, entry.getValue().getAsString(), jsonElement.getAsString());
					continue;
				}
			}

			if (entry.getValue().isJsonObject()) {
				setIndex(i);
				pathBuilder.append(PATH_OBJECT);
				setObjectKey(entry);
				checkJsonObj(entry.getValue().getAsJsonObject(), jsonElement.getAsJsonObject());
				continue;
			}

			if (entry.getValue().isJsonArray()) {
				if (checkRuleMap(entry.getKey(), RuleEnum.IS_JSON_ARRAY)) {
					continue;
				}
				setIndex(i);
				pathBuilder.append(PATH_ARRAY);
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

	private boolean checkIgnoreGlobalList(RuleEnum ruleEnum) {
		if (ignoreGlobalList.size() == 0) {
			return false;
		}
		if (ignoreGlobalList.contains(ruleEnum)) {
			return true;
		}
		return false;
	}

	private boolean checkIgnoreJsonPath(JsonDiff jsonDiff) {
		if (ignoreJsonDiffList.size() == 0) {
			return false;
		}
		if (ignoreJsonDiffList.contains(jsonDiff)) {
			return true;
		}
		return false;
	}

	private boolean checkRegexMap(String entryKey, String entryValue) {
		if (regexMap.size() == 0) {
			return false;
		}
		if (regexMap.containsKey(entryKey)) {
			if (entryValue.matches(regexMap.get(entryKey))) {
				return true;
			}
		}

		return false;
	}

	private boolean checkRuleMap(String entryKey, RuleEnum ruleEnum) {
		if (ruleMap.size() == 0) {
			return false;
		}
		if (ruleMap.containsKey(entryKey) && ruleMap.get(entryKey).equals(ruleEnum)) {
			return true;
		}

		return false;
	}

	private boolean checkRuleConstant(String entryKey, String constant) {
		if (entryKey.equals(constant)) {
			return true;
		}
		return false;
	}

	private boolean checkRuleMap(String entryKey) {
		if (ruleMap.size() == 0) {
			return false;
		}
		if (ruleMap.containsKey(entryKey) && (ruleMap.get(entryKey).equals(RuleEnum.IS_ANY_INTEGER)
				|| ruleMap.get(entryKey).equals(RuleEnum.IS_ANY_STRING)
				|| ruleMap.get(entryKey).equals(RuleEnum.IS_TIMESTEMP))) {
			return true;
		}

		return false;
	}

}
