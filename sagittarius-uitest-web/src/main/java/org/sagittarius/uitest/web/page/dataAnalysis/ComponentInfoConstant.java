package org.sagittarius.uitest.web.page.dataAnalysis;

public interface ComponentInfoConstant {

	String HDFS_PATH = "hdfs_path";
	String SCRIPT_TYPE = "script_type";

	enum ScriptType {
		DATA_EXTRACT, DATA_PRETREATMENT, DATA_TRAIN, DATA_GRADE
	}

}
