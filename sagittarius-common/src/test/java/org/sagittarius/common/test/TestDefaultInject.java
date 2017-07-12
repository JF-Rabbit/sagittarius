package org.sagittarius.common.test;

import org.junit.Test;
import org.sagittarius.common.annotation.DefaultInjectResolver;
import org.sagittarius.common.test.entity.Person;

public class TestDefaultInject {
	@Test // 已经赋值
	public void test1() throws IllegalArgumentException, IllegalAccessException {
		Person person = new Person();
		person.setName("www");
		person.setAge(20);
		DefaultInjectResolver.defaultInject(person);
		System.out.println(person);
	}

	@Test // 没有赋值
	public void test2() throws IllegalArgumentException, IllegalAccessException {
		Person person = new Person();
		DefaultInjectResolver.defaultInject(person);
		System.out.println(person);
	}

	@Test // 不注入
	public void test3() throws IllegalArgumentException, IllegalAccessException {
		Person person = new Person();
		System.out.println(person);
	}
	
	
}
