package org.sagittarius.test;

import org.sagittarius.uitest.WebTest;
import org.sagittarius.uitest.util.BaseAction;
import org.sagittarius.uitest.util.DemoAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class TestAction1 extends WebTest {

    @Autowired
    private DemoAction demoAction;

    @Test
    public void test() {
        manager.getConsoleIgnores().add("ss1");
        manager.getConsoleIgnores().add("sp0");
        demoAction.do_test("http://www.baidu.com", "123");
    }

//    @Autowired
//    private BaseAction baseAction;
//
//    @Test
//    public void test1() {
//        baseAction.open("http://www.baidu.com");
//        baseAction.sleep();
//    }
}
