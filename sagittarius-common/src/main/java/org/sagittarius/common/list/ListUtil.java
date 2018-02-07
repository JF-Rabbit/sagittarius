package org.sagittarius.common.list;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    @SafeVarargs
	public static <T> List<T> multiAppend(List<T> list, T... params) {
        for (T p : params) {
            list.add(p);
        }
        return list;
    }

    @SafeVarargs
	public static <T> List<T> multiAppend(T... params) {
        List<T> list = new ArrayList<>();
        for (T p : params) {
            list.add(p);
        }
        return list;
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