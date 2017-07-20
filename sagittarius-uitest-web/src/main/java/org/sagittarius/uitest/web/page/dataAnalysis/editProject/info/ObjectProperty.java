package org.sagittarius.uitest.web.page.dataAnalysis.editProject.info;

public class ObjectProperty {
	private String propertyName;
	private DataTypeEnum dataType;
	private String fieldValue;

	public ObjectProperty() {
		super();
	}

	public ObjectProperty(String propertyName, DataTypeEnum dataType, String fieldValue) {
		super();
		this.propertyName = propertyName;
		this.dataType = dataType;
		this.fieldValue = fieldValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public DataTypeEnum getDataType() {
		return dataType;
	}

	public void setDataType(DataTypeEnum dataType) {
		this.dataType = dataType;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

}
