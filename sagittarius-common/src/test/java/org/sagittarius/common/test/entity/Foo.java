package org.sagittarius.common.test.entity;

public class Foo {

    private int foo_id;
    private String foo_name;
    private int age;
    private String something;

    @Override
    public String toString() {
        return "Foo{" +
                "foo_id=" + foo_id +
                ", foo_name='" + foo_name + '\'' +
                ", age=" + age +
                ", something='" + something + '\'' +
                '}';
    }
}
