package org.sagittarius.test;

import org.sagittarius.uitest.WebTest;
import org.sagittarius.uitest.util.DemoAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class TestAction1 extends WebTest {

    @Autowired
    private DemoAction demoAction;

    @Test
    public void test() {
        demoAction.do_test("http://www.baidu.com", "123");
    }
}
