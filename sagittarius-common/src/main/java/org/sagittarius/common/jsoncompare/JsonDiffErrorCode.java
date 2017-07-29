package org.sagittarius.common.jsoncompare;

public interface JsonDiffErrorCode {

	String RESPONSE_STATUS_MISS_MATCH = "\nRESPONSE_STATUS_MISS_MATCH:\n";
	String KEY_NOT_FOUND = "\nKEY_NOT_FOUND:\n";
	String VALUE_MISS_MATCH = "\nVALUE_MISS_MATCH:\n";
	String VALUE_MISS_REGEX = "\nVALUE_MISS_REGEX:\n";
	String JSON_TYPE_MISS_MATCH = "\nJSON_TYPE_MISS_MATCH:\n";
	String JSON_OBJECT_SIZE_MATCH = "\nJSON_OBJECT_SIZE_MATCH:\n";
	String JSON_ARRAY_SIZE_MISS_MATCH = "\nJSON_ARRAY_SIZE_MISS_MATCH:\n";

}
