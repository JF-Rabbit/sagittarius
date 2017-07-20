package org.sagittarius.uitest.web.test.testenum;

import org.sagittarius.uitest.web.test.demo.TestCRUD;
import org.sagittarius.uitest.web.test.testcase.TestKMXDemo;

public enum RunCaseEnum {

	TEST_KMX_DEMO(TestKMXDemo.class) {
		@Override
		public Class<?> run() {
			return getRunClass(TEST_KMX_DEMO);
		}
	},
	
	TestCRUD(TestCRUD.class) {
		@Override
		public Class<?> run() {
			return null;
		}
	};

	private Class<?> clazz;

	private RunCaseEnum(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return clazz;
	}
	
	Class<?> getRunClass(RunCaseEnum runCaseEnum){
		return runCaseEnum.getClazz();
	}

	public abstract Class<?> run();

}
