package org.sagittarius.uitest.util.mobile;

public enum LoadElementConfirmType {
	/** isDisplay */
	IS_DISPLAYED,
	/** Text: getText().equals(String expect) */
	GET_TEXT_EQUALS,
	/** content-desc: getAttribute("name").equals(String expect) */
	GET_CONTENT_DESC_EQUALS;
}
