package org.sagittarius.uitest.web.test;

import org.sagittarius.junit.Executer;
import org.sagittarius.uitest.web.test.testenum.RunCaseEnum;

public class TestRunJunit {

	public static void main(String[] args) {
		Executer.run(RunCaseEnum.TEST_KMX_DEMO.run());//
	}
}
