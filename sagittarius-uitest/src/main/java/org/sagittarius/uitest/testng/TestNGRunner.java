package org.sagittarius.uitest.testng;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestNGListener;
import org.testng.TestNG;

public interface TestNGRunner {

	default void run(Class<?>... classes) {
		TestNG testng = new TestNG();
		testng.setTestClasses(classes);
		List<Class<? extends ITestNGListener>> list = new ArrayList<>();
		list.add(TestNGListener.class);
		testng.setListenerClasses(list);
		testng.run();
	}
}
