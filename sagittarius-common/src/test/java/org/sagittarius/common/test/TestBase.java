package org.sagittarius.common.test;

import org.sagittarius.common.judge.JudgeUtil;
import org.sagittarius.common.test.entity.Person;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestBase {
	
	@Test
	public void test_JudgeUtil_isObjHaveNotNullField() throws IllegalArgumentException, IllegalAccessException {
		Person person = new Person();
		System.out.println(person);
		Assert.assertEquals(JudgeUtil.isObjHaveNullField(person), false);
		person.setName("11");
		Assert.assertEquals(JudgeUtil.isObjHaveNullField(person), false);
		person.setAge(18);
		Assert.assertEquals(JudgeUtil.isObjHaveNullField(person), true);
	}

}
