package org.sagittarius.common;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public interface FunctionUtil {

    Predicate<Object> isNull = n -> n == null;

    Predicate<String> strNull = n -> n == null;
    Predicate<String> strEmpty = n -> n.trim().length() == 0;

    Predicate<List<?>> listNull = n -> n == null;
    Predicate<List<?>> listEmpty = n -> n.size() == 0;

    Predicate<Map<?, ?>> mapNull = n -> n == null;
    Predicate<Map<?, ?>> mapEmpty = n -> n.size() == 0;

    Function<String, Boolean> EMPTY_STR = n -> strNull.or(strEmpty).test(n);
    Function<String, Boolean> NOT_EMPTY_STR = n -> strNull.negate().and(strEmpty.negate()).test(n);

    Function<List<?>, Boolean> EMPTY_LIST = n -> listNull.or(listEmpty).test(n);
    Function<List<?>, Boolean> NOT_EMPTY_LIST = n -> listNull.negate().and(listEmpty.negate()).test(n);

    Function<Map<?, ?>, Boolean> EMPTY_MAP = n -> mapNull.or(mapEmpty).test(n);
    Function<Map<?, ?>, Boolean> NOT_EMPTY_MAP = n -> mapNull.negate().and(mapEmpty.negate()).test(n);

}
