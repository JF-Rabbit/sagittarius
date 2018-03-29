package org.sagittarius.uitest.web.test.demo;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sagittarius.common.IOUtil;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class NorTest2 extends AbstractJUnit4SpringContextTests{

	@Test
	public void test01() throws IOException {
		System.out.println(IOUtil.getFilePathFromClassLoader("spring.xml"));
		
         System.out.println(System.getProperty("user.dir"));
        
	}
	
}
