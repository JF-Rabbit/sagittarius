package org.sagittarius.common.list;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    public static <T> List<T> multiAppend(List<T> list, T... params) {
        for (T p : params) {
            list.add(p);
        }
        return list;
    }

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();

        list = ListUtil.multiAppend(list, 1, "11", 3.14);
        System.out.println(list);
    }

}