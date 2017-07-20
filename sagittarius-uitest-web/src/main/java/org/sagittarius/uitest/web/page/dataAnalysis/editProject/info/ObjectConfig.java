package org.sagittarius.uitest.web.page.dataAnalysis.editProject.info;

import java.util.List;

public class ObjectConfig {

	private String objectName;
	private List<ObjectProperty> objectProperty;

	public ObjectConfig() {
		super();
	}

	public ObjectConfig(String objectName, List<ObjectProperty> objectProperty) {
		super();
		this.objectName = objectName;
		this.objectProperty = objectProperty;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public List<ObjectProperty> getObjectProperty() {
		return objectProperty;
	}

	public void setObjectProperty(List<ObjectProperty> objectProperty) {
		this.objectProperty = objectProperty;
	}

}
