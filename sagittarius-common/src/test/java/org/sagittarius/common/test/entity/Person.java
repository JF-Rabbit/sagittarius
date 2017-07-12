package org.sagittarius.common.test.entity;

import org.sagittarius.common.annotation.DefaultInject;

public class Person {

	@DefaultInject("qqq")
	private Object name;
	@DefaultInject("18")
	private Object age;

	/**
	 * 
	 */
	public Person() {
		super();
	}

	/**
	 * 
	 * @param name
	 * @param age
	 */
	public Person(Object name, Object age) {
		super();
		this.name = name;
		this.age = age;
	}

	public Object getName() {
		return name;
	}

	public void setName(Object name) {
		this.name = name;
	}

	public Object getAge() {
		return age;
	}

	public void setAge(Object age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}

}
