package org.sagittarius.uitest.util;

import org.openqa.selenium.By;
import org.sagittarius.uitest.driver.DriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("prototype")
public class DemoAction extends BaseAction {

    @Autowired
    private DriverManager manager;

    @PostConstruct
    public void init() {
        setDriver(manager.getDriver());
    }

    public void do_test(String url, String keys) {
        open(url);

        sendKeys(find(By.id("kw")), keys);

        sleep();

        click(find(By.id("su")));

        sleep(2000);

    }
}
