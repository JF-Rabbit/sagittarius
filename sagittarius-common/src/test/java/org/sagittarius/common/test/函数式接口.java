package org.sagittarius.common.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.sagittarius.common.ListFilter;
import org.sagittarius.common.test.entity.User;
import org.sagittarius.common.test.entity.UserFactory;

/**
 * java8 新特性
 * 
 * @author jasonzhang
 *
 */
public class 函数式接口 {

	/** Predicate接口 */
	/**
	 * 用来判断是否满足某个或多个条件
	 */
	public static void predicateDemo() {
		Predicate<String> startsWithJ = (n) -> n.endsWith("a");
		Predicate<String> fourLetterLong = (n) -> n.length() == 4;
		System.out.println(startsWithJ.test("Java"));
		System.out.println(fourLetterLong.test("PHP"));
		System.out.println(startsWithJ.and(fourLetterLong).test("Java"));
	}

	/** Function 接口 */
	/**
	 * 定义一个方法
	 */
	public static void functionDemo() {
		Function<String, String> function1 = (n) -> n.toUpperCase();
		Function<String, Integer> function2 = (n) -> n.length();
		System.out.println(function1.apply("qwert"));
		System.out.println(function2.apply("qwert"));
	}

	/** Optional 接口 */
	/**
	 * 用来判断一个对象为null或不为null时的后续操作
	 */
	public static void optionalDemo() {
		User user1 = new User();
		user1.setName("Jone");
		user1 = null;
		Optional<User> optional = Optional.ofNullable(user1);

		// 如果不为null 则进行操作
		optional.ifPresent((v) -> {
			System.out.println(v);
		});

		// 如果不为null则返回，为null则返回默认值
		System.out.println(optional.orElse(new User()));

		// 如果不为null则返回，为null则通过方法创建
		System.out.println(optional.orElseGet(() -> {
			User u = new User();
			u.setName("Marry");
			return u;
		}));

		// 如果不为null则可以不断的关联方法进行后续操作，为null则返回空的Optional
		System.out.println(optional.map(u -> u.getName()).map(u -> u.toLowerCase()).map(u -> u.length()).orElse(null));
		System.out.println(optional.map(u -> u.getName()).map(u -> u.toLowerCase()).map(u -> u.length()).orElseThrow(NullPointerException::new)); // 返回一个异常
	}

	/** Supplier 接口 */
	/**
	 * 对象工厂，调用时返回一个对象
	 */
	public static void supplierDemo() {

		Supplier<User> userSupplier1 = User::new;
		Supplier<User> userSupplier2 = () -> {
			return new User("Katy");
		};
		System.out.println(userSupplier1.get());
		System.out.println(userSupplier2.get());
	}

	/** Consumer 接口 */
	/**
	 * 定义一个单个元素操作的方法
	 */
	public static void consumerDemo() {
		UserFactory<User> userFactory = User::new;

		Consumer<User> one = (user) -> System.out.println(user.getName());
		one.accept(userFactory.create("Marry"));
	}

	/** Stream 接口 */
	/**
	 * 应用在一组元素上一次执行的操作序列，支持list和set
	 */
	public static void streamDemo1() {
		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");

		/** Filter 过滤 */
		stringCollection.stream().filter((s) -> s.startsWith("a")).forEach(System.out::println);
		/** Sort 排序 */
		stringCollection.stream().sorted().filter((s) -> s.startsWith("a")).forEach(System.out::println);
		System.out.println(stringCollection); // 原数据不会被修改
		/** Map 映射 */
		stringCollection.stream().map(String::toUpperCase).sorted((a, b) -> b.compareTo(a)).forEach(System.out::println);
		/** Match 匹配 */
		boolean anyStartsWithA = stringCollection.stream().anyMatch((s) -> s.startsWith("a"));
		System.out.println(anyStartsWithA); // true
		boolean allStartsWithA = stringCollection.stream().allMatch((s) -> s.startsWith("a"));
		System.out.println(allStartsWithA); // false
		boolean noneStartsWithZ = stringCollection.stream().noneMatch((s) -> s.startsWith("z"));
		System.out.println(noneStartsWithZ); // true

		/** Count 计数 */
		long startsWithB = stringCollection.stream().filter((s) -> s.startsWith("b")).count();
		System.out.println(startsWithB); // 3

		/** Reduce 规约 多个元素规约为一个元素 */
		Optional<String> reduced = stringCollection.stream().sorted().reduce((s1, s2) -> s1 + "#" + s2);
		reduced.ifPresent((o) -> System.out.println(o));
		// "aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2"
	}

	/** 并行 Streams */
	public static void streamDemo2() {
		int max = 1000000;
		List<String> values = new ArrayList<>(max);
		for (int i = 0; i < max; i++) {
			UUID uuid = UUID.randomUUID();
			values.add(uuid.toString());
		}

		long t0 = System.nanoTime();
		long count1 = values.stream().sorted().count();
		System.out.println(count1);
		long t1 = System.nanoTime();
		long millis1 = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		System.out.println(String.format("sequential sort took: %d ms", millis1));

		long t2 = System.nanoTime();
		long count2 = values.parallelStream().sorted().count(); // 并行排序
		System.out.println(count2);
		long t3 = System.nanoTime();
		long millis2 = TimeUnit.NANOSECONDS.toMillis(t3 - t2);
		System.out.println(String.format("parallel sort took: %d ms", millis2));
	}

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
