package org.sagittarius.common.annotation;

import org.sagittarius.common.reflect.ReflectUnit;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

public class ParamFilterResolver {

    private static final String PARAM = "PARAM: ";
    private static final String IS_NULL = " IS NULL";
    private static final String IS_EMPTY = " IS EMPTY";

    private static void checkNull(String name, Object param) throws ParamFilterException {
        if (param == null)
            throw new ParamFilterException(PARAM + name + IS_NULL);

    }

    private static void checkEmptyString(String name, Object param) throws ParamFilterException {
        checkNull(name, param);
        if (String.valueOf(param).trim().length() == 0)
            throw new ParamFilterException(PARAM + name + IS_EMPTY);

    }

    private static void checkEmptyList(String name, List param) throws ParamFilterException {
        checkNull(name, param);
        if (param.size() == 0)
            throw new ParamFilterException(PARAM + name + IS_EMPTY);
    }

    private static void checkEmptyMap(String name, Map param) throws ParamFilterException {
        checkNull(name, param);
        if (param.size() == 0)
            throw new ParamFilterException(PARAM + name + IS_EMPTY);
    }

    public static void checkParam(Object obj) throws ParamFilterException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (Modifier.toString(field.getModifiers()).contains("private")) {
                field.setAccessible(true);
            }
            if (field.isAnnotationPresent(ParamFilter.class)) {
                ParamFilterEnum[] rules = field.getAnnotation(ParamFilter.class).value();
                Object paramValue = ReflectUnit.getField(field, obj);
                for (ParamFilterEnum rule : rules) {
                    switch (rule) {
                        case NOT_NULL:
                            checkNull(field.getName(), paramValue);
                            break;
                        case NOT_EMPTY_STRING:
                            checkEmptyString(field.getName(), paramValue);
                            break;
                        case NOT_EMPTY_LIST:
                            checkEmptyList(field.getName(), (List) paramValue);
                            break;
                        case NOT_EMPTY_MAP:
                            checkEmptyMap(field.getName(), (Map) paramValue);
                            break;
                    }
                }
            }
        }
    }
}
