package org.sagittarius.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClassSortDemo {

    private int id;
    private String name;
    private long start;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public ClassSortDemo(int id, String name, long start) {
        this.id = id;
        this.name = name;
        this.start = start;
    }

    @Override
    public String toString() {
        return "SortDemo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", start=" + start +
                '}';
    }

    public static long toLong(String source) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source).getTime();
    }

    public enum SortEnum {
        ID, NAME, START
    }

    public static void sort(List<ClassSortDemo> original, SortEnum sortEnum, boolean isUpperSort) {
        switch (sortEnum) {
            case ID:
                original.sort(Comparator.comparing(ClassSortDemo::getId));
                if (!isUpperSort) {
                    Collections.reverse(original);
                }
                break;
            case NAME:
                original.sort(Comparator.comparing(ClassSortDemo::getName));
                if (!isUpperSort) {
                    Collections.reverse(original);
                }
                break;
            case START:
                original.sort(Comparator.comparing(ClassSortDemo::getStart));
                if (!isUpperSort) {
                    Collections.reverse(original);
                }
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) throws ParseException {
        ClassSortDemo demo1 = new ClassSortDemo(1024, "FG_lIjUI_000",
                ClassSortDemo.toLong("2017-11-30 15:28:28")
        );
        ClassSortDemo demo2 = new ClassSortDemo(3309, "fgName_haebq",
                ClassSortDemo.toLong("2017-11-29 17:32:34")
        );
        ClassSortDemo demo3 = new ClassSortDemo(-231, "lintest_TAOh6",
                ClassSortDemo.toLong("2017-11-29 19:30:41")
        );
        List<ClassSortDemo> list = new ArrayList<>();
        list.add(demo1);
        list.add(demo2);
        list.add(demo3);

        list.forEach(System.out::println);
        System.out.println("\n=============================\n");

        ClassSortDemo.sort(list, ClassSortDemo.SortEnum.ID, true);
        list.forEach(System.out::println);
        System.out.println("\n=============================\n");

        ClassSortDemo.sort(list, SortEnum.NAME, false);
        list.forEach(System.out::println);
        System.out.println("\n=============================\n");

        ClassSortDemo.sort(list, SortEnum.START, true);
        list.forEach(System.out::println);
    }
}

