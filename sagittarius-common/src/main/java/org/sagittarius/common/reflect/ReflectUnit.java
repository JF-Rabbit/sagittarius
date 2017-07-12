package org.sagittarius.common.reflect;

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
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static Object reflectCall(Object instanceClass, String methodName, Object... args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class<?> clazz = instanceClass.getClass();
		if (args == null) {
			Method method = clazz.getMethod(methodName);
			return method.invoke(instanceClass);
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
					return method.invoke(instanceClass, args);
				}
			}
		}
		return null;
	}

	public static Object getInstanceClassByClassName(String className) throws Exception {
		return Class.forName(className).newInstance();
	}
}
