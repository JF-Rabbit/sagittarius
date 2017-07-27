package org.sagittarius.common.test;

import java.lang.reflect.Method;

import org.testng.annotations.Test;


public class ReflectTest {

	public void test01(int a) {

	}

	@Test
	public void test() throws InstantiationException, IllegalAccessException {
		Class<?> clazz = ReflectTest.class.newInstance().getClass();
		Method[] methods = clazz.getMethods();
		for (Method m: methods) {
			System.out.println(m.getName() + " " +m.getParameterTypes());
		}
	}

}
