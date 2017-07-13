package org.sagittarius.common.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.sagittarius.common.ListFilter;

public class TestListFilter {
	public static void main(String[] args) {
		List<Object> list = new ArrayList<>();
		list.add(9);
		list.add(1);
		list.add(8);
		list.add(-1);

		Predicate<Object> minNum = (n) -> Integer.valueOf(n.toString()) > 0;
		Predicate<Object> maxNum = (n) -> Integer.valueOf(n.toString()) < 8;

		ListFilter.predicateFilter(list, minNum, maxNum).forEach(System.out::print);
	}
}
