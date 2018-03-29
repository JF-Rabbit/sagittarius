package org.sagittarius.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtil {

    @SafeVarargs
	public static <T> List<T> addAll(List<T> list, T... params) {
        list.addAll(Arrays.asList(params));
        return list;
    }

    @SafeVarargs
	public static <T> List<T> asList(T... params) {
        return Arrays.asList(params);
    }

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }

    public static <T> List<T> removeAll(List<T> collection, List<T> remove) {
        List<T> list = new ArrayList<>();
        for (T obj : collection) {
            if (!remove.contains(obj)) {
                list.add(obj);
            }
        }
        return list;
    }

}