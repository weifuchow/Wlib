package com.weifuchow.jdk.learn.jvm;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ChangePrivateStaticField {
    public static final boolean FLAG = Boolean.parseBoolean("false");

    static void setFinalStatic(Class clazz, String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Field modifiers = field.getClass().getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    public static void main(String... args) throws Exception {
        System.out.printf("Everything is %s%n", FLAG);
        setFinalStatic(ChangePrivateStaticField.class, "FLAG", true);
        System.out.printf("Everything is %s%n", FLAG);
    }
}
