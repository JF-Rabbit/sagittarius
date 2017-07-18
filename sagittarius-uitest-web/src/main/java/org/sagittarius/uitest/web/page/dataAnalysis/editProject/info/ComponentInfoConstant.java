package org.sagittarius.uitest.web.page.dataAnalysis.editProject.info;

public interface ComponentInfoConstant {

	String HDFS_PATH = "hdfs_path";
	String SCRIPT_TYPE = "script_type";
	String TABLE_NAME = "table_name";
	String FIELD_ARRAY = "field_array";

	enum ScriptTypeEnum {
		DATA_EXTRACT, DATA_PRETREATMENT, DATA_TRAIN, DATA_GRADE
	}

}
