package com.ela.elacn.Util;

import java.util.Collection;
import java.util.List;

public class SetUtil {

    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static <T> T first(List<T> list) {
        return isEmpty(list) ? null : list.iterator().next();
    }

    public static <T> boolean everyEquals(List<T> list, T e) {
        for(T t : list)
            if(!equals(t, e))
                return false;
        return true;
    }

    private static boolean equals(Object a , Object b) {
        return a == null ? b == null : a.equals(b);
    }

}
