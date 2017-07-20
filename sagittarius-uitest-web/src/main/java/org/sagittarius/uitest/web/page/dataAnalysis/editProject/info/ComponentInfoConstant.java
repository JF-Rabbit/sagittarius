package org.sagittarius.uitest.web.page.dataAnalysis.editProject.info;

public interface ComponentInfoConstant {
	
	String TRUE = "true";
	String FALSE = "false";

	String HDFS_PATH = "hdfs_path";
	
	String FIELD_TABLE_NAME = "field_table_name";
	String FIELD_ARRAY = "field_array";
	
	String HAVE_QUERY_CONDION = "have_query_condion";
	String QUERY_CONDION_IS_STATIC_TIMEQUERY_CONDION = "query_condition_is_static_timequery_condition";
	String QUERY_CONDION_TIME_START = "query_condition_time_start";
	String QUERY_CONDION_TIME_END = "query_condition_time_end";
	String QUERY_CONDION_ID = "query_condition_id";
	String QUERY_CONDION_OTHER = "query_condition_other";
	
	String GROUP_VALUE = "group_value";
	String GROUP_YEAR_MONTH_DAY = "Year-Month-Day";
	String GROUP_YEAR_MONTH = "Year-Month";
	String GROUP_YEAR = "Year";
	
	String OBJECT_TYPE_LIST = "object_type_list";
	
	String SCRIPT_TYPE = "script_type";

	enum ScriptTypeEnum {
		DATA_EXTRACT, DATA_PRETREATMENT, DATA_TRAIN, DATA_GRADE
	}

}
