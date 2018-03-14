package org.sagittarius.uitest.util;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DemoAction{

    @Resource
    BaseAction baseAction;


    public void do_test(String url, String keys) {
        System.out.println(baseAction.driver());

        baseAction.open(url);

        baseAction.sendKeys(baseAction.find(By.id("kw")), keys);

        baseAction.sleep();

        baseAction.click(baseAction.find(By.id("su")));

        baseAction.sleep(2000);

    }
}
