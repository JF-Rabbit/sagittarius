package org.sagittarius.junit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JunitDemo2 {
	static int b;
	
	@BeforeClass
	public static void boforeClass() {
		System.out.println("== JunitDemo2 ==");
		b = 2;
	}
	
	@Before
	public void bofore() {
		System.out.println("bofore");
	}

	@After
	public void after() {
		System.out.println("after");
	}

	@Test
	public void test2() {
		System.out.println("test2");
		Assert.assertEquals(1, 1);
	}

}
