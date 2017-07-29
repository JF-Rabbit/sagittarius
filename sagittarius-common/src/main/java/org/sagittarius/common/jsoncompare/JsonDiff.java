package org.sagittarius.common.jsoncompare;

public class JsonDiff {

	private String errorCode;
	private String errorPath;
	private String errorMsg;

	public JsonDiff() {
		super();
	}
	
	/**
	 * @param errorCode
	 * @param errorPath
	 */
	public JsonDiff(String errorCode, String errorPath) {
		super();
		this.errorCode = errorCode;
		this.errorPath = errorPath;
	}



	public JsonDiff(String errorCode, String errorPath, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorPath = errorPath;
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "JsonDiff [errorCode=" + errorCode + ", errorPath=" + errorPath + ", errorMsg=" + errorMsg + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorPath == null) ? 0 : errorPath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JsonDiff other = (JsonDiff) obj;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (errorPath == null) {
			if (other.errorPath != null)
				return false;
		} else if (!errorPath.equals(other.errorPath))
			return false;
		return true;
	}

}
