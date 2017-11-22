package org.sagittarius.uitest.util.web.js;

public interface JsConstant {
	
	String WINDOW_SCREEN_AVAIL_WIDTH = "var obj = window.screen.availWidth; return obj;";
	String WINDOW_SCREEN_AVAIL_HEIGHT = "var obj = window.screen.availHeight; return obj;";
	String DOCUMENT_BODY_CLIENT_WIDTH = "var obj = document.body.clientWidth ; return obj;";
	String DOCUMENT_BODY_CLIENT_HEIGHT = "var obj = document.body.clientHeight; return obj;";
	String SCROLL_TO_TOP = "arguments[0].scrollIntoView(true);";
	String SCROLL_TO_BOTTOM = "arguments[0].scrollIntoView(false);";

}
