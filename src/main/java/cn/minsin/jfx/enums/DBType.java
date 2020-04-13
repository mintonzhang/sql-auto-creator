package cn.minsin.jfx.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: minton.zhang
 * @since: 2020/4/6 19:05
 */
public enum DBType {
    Mysql("Mysql", cn.minsin.jfx.enums.Mysql.class),
    Oracle("Oracle", cn.minsin.jfx.enums.Mysql.class);
    private String name;

    private Class<?> clazz;

    DBType(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public static String findByName(String type, String charset) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DBType classByName = findClassByName(type);
        if (classByName != null) {
            Method findByName = classByName.clazz.getDeclaredMethod("findByName", String.class);
            return findByName.invoke(classByName.clazz, charset).toString();
        }
        return null;
    }

    private static DBType findClassByName(String name) {
        for (DBType value : DBType.values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

}
