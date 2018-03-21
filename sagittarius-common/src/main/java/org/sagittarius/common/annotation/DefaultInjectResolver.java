package org.sagittarius.common.annotation;

import org.sagittarius.common.reflect.ReflectUnit;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * DefaultInject 默认值注入解析器
 *
 * @author JasonZhang 2017年7月3日 下午6:49:27
 */
public class DefaultInjectResolver {

    /**
     * 注入实例对象属性中被注解DefaultInject修饰的属性值
     *
     * @param obj 实例对象
     */
    public static void defaultInject(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (Modifier.toString(field.getModifiers()).contains("private")) {
                field.setAccessible(true);
            }
            if (ReflectUnit.getField(field, obj) == null && field.isAnnotationPresent(DefaultInject.class)) {
                ReflectUnit.setField(field, obj, field.getAnnotation(DefaultInject.class).value());
            }
        }
    }

}
