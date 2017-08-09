package org.sagittarius.common.jsoncompare;

public class JsonCompareCheckPoint {

	private String entryKey;
	private CheckEnum checkPoint;
	private String checkKey;

	public JsonCompareCheckPoint() {
		super();
	}

	public JsonCompareCheckPoint(String entryKey, CheckEnum checkPoint, String checkKey) {
		super();
		this.entryKey = entryKey;
		this.checkPoint = checkPoint;
		this.checkKey = checkKey;
	}

	public String getEntryKey() {
		return entryKey;
	}

	public void setEntryKey(String entryKey) {
		this.entryKey = entryKey;
	}

	public CheckEnum getCheckPoint() {
		return checkPoint;
	}

	public void setCheckPoint(CheckEnum checkPoint) {
		this.checkPoint = checkPoint;
	}

	public String getCheckKey() {
		return checkKey;
	}

	public void setCheckKey(String checkKey) {
		this.checkKey = checkKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkKey == null) ? 0 : checkKey.hashCode());
		result = prime * result + ((checkPoint == null) ? 0 : checkPoint.hashCode());
		result = prime * result + ((entryKey == null) ? 0 : entryKey.hashCode());
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
		JsonCompareCheckPoint other = (JsonCompareCheckPoint) obj;
		if (checkKey == null) {
			if (other.checkKey != null)
				return false;
		} else if (!checkKey.equals(other.checkKey))
			return false;
		if (checkPoint != other.checkPoint)
			return false;
		if (entryKey == null) {
			if (other.entryKey != null)
				return false;
		} else if (!entryKey.equals(other.entryKey))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JsonCompareCheckPoint [entryKey=" + entryKey + ", checkPoint=" + checkPoint + ", checkKey=" + checkKey
				+ "]";
	}

}
