package org.sagittarius.common.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * 
 * @author jasonzhang 2017年6月16日 上午9:07:05
 *
 */
public class ReflectUnit {

	private static final String INTEGER = "Integer";
	private static final String INT = "int";
	private static final String CHARACTER = "Character";
	private static final String CHAR = "char";
	// XXX 未添加全所有基本类型

	private static String[] filterArgsName(Object... args) {
		String[] argsName = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			if (args[i].getClass().getSimpleName().equals(INTEGER)) {
				argsName[i] = INT;
			} else if (args[i].getClass().getSimpleName().equals(CHARACTER)) {
				argsName[i] = CHAR;
			} else {
				argsName[i] = args[i].getClass().getSimpleName();
			}
		}
		return argsName;
	}

	/**
	 * 反射执行方法
	 * <p>
	 * Demo:反射无参方法
	 * <p>
	 * public void test1() {}
	 * <p>
	 * ReflectUnit.reflectCall(ReflectUnit.class.newInstance(), "test1", null);
	 * <p>
	 * ReflectUnit.reflectCall(ReflectUnit.getInstanceClassByClassName("common.ReflectUnit"),
	 * "test1", null);
	 * <p>
	 * Demo:反射有参方法
	 * <p>
	 * public void test2(int a, String b, char c) { }
	 * <p>
	 * ReflectUnit.reflectCall(ReflectUnit.class.newInstance(), "test2", 1, "Q",
	 * 'w');
	 * <p>
	 * ReflectUnit.reflectCall(ReflectUnit.getInstanceClassByClassName("common.ReflectUnit"),
	 * "test2", 1, "Q", 'w');
	 * 
	 * @param instanceClass
	 *            实例类
	 * @param methodName
	 *            方法名
	 * @param args
	 *            变长参数数组，无参时传null
	 * @return 原方法返回值(void方法返回null)
	 */
	public static Object reflectCall(Object instanceClass, String methodName, Object... args) {
		Class<?> clazz = instanceClass.getClass();
		if (args == null) {
			Method method = getMethod(clazz, methodName);
			return invoke(method, instanceClass);
		}
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				if (args.length != method.getParameterTypes().length) {
					continue;
				}

				boolean flag = true;
				for (int i = 0; i < args.length; i++) {
					if (!method.getParameterTypes()[i].getSimpleName().equals(filterArgsName(args)[i])) {
						flag = false;
						break;
					}
				}
				if (!flag) {
					continue;
				} else {
					return invoke(method, instanceClass, args);
				}
			}
		}
		return null;
	}

	public static Object newInstance(String className) {
		try {
			return Class.forName(className).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object getField(Field field, Object obj) {
		try {
			return field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static void setField(Field field, Object obj, Object value) {
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Method getMethod(Class<?> clazz, String methodName) {
		try {
			return clazz.getMethod(methodName);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object invoke(Method method, Object obj) {
		try {
			return method.invoke(obj);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object invoke(Method method, Object obj, Object... args) {
		try {
			return method.invoke(obj, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
