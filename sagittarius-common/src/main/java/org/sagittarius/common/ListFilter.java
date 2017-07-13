package org.sagittarius.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * List元素过滤器
 * 
 * @author jasonzhang
 *
 */
public class ListFilter {
	/**
	 * 根据过滤条件过滤list
	 * 
	 * <p>
	 * Demo:
	 * <p>
	 * List<Object> list = new ArrayList<>();
	 * <p>
	 * list.add(9); list.add(1); list.add(8); list.add(-1);
	 * <p>
	 * Predicate<Object> minNum = (n) -> Integer.valueOf(n.toString()) > 0;
	 * <p>
	 * Predicate<Object> maxNum = (n) -> Integer.valueOf(n.toString()) < 8;
	 * <p>
	 * ListFilter.predicateFilter(list, minNum,
	 * maxNum).forEach(System.out::print);
	 * 
	 * @param list
	 *            需要过滤的list
	 * @param predicates
	 *            过滤条件数组
	 * @return 返回过滤后的元素组成的list
	 */
	@SafeVarargs
	public static List<Object> predicateFilter(List<Object> list, Predicate<Object>... predicates) {
		List<Object> result = new ArrayList<>();
		Predicate<Object> original = (obj) -> true;
		for (Predicate<Object> predicate : predicates) {
			original = original.and(predicate);
		}
		list.stream().filter((Predicate<? super Object>) original).forEach((n) -> result.add(n));
		return result;
	}
}
