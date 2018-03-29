package org.sagittarius.common.test;

import org.sagittarius.common.YamlUtil;

public class TestYamlUtil {

    public static void main(String[] args) {
        Object object = YamlUtil.load("C:/Users/kzdatd/Desktop/env.yaml");
        System.out.println(object);

        Object foo = YamlUtil.chain(object).asMap("qa_dev").asMap("foo").value("foo_name");
        System.out.println(foo);
    }
}
