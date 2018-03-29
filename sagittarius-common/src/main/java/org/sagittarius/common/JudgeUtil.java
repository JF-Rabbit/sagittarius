package org.sagittarius.common;

import java.lang.reflect.Field;

public class JudgeUtil {

    public static boolean isNullStr(String str) {
        return str != null && str.equals("");
    }

    public static boolean isNotNullStr(String str) {
        return !isNullStr(str);
    }

    /**
     * 判断实体类中是否还有null的属性
     */
    public static boolean isObjHaveNullField(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (ReflectUnit.getField(field, obj) == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTrue(Object obj) {
        return obj != null && obj.toString().toLowerCase().equals("true");
    }

    public static boolean isFalse(Object obj) {
        return obj != null && obj.toString().toLowerCase().equals("false");
    }

}
